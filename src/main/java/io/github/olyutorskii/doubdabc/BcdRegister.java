/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

/**
 * BCD register with Double-Dabble algorithm operation.
 *
 * <p>BCD register is flexible pseudo register of you specified size.
 * BCD register is layouted on int array.
 *
 * <p>Each decimal columns are layouted 4bit nibble width on BCD register.
 * <ul>
 * <li>Lower decimal digit is layouted on lower int bits.
 * <li>Lower decimal digit is layouted on younger int array element.
 * </ul>
 *
 * <p>There is 1bit binary input stream from LSB.
 *
 * <p>Each decimal digit overlaps Packed-BCD and Bi-quinary coded decimal.
 *
 * <p>Signed(negative) decimal value is not supported.
 *
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Double_dabble">
 * Double dabble (Wikipedia)
 * </a>
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Binary-coded_decimal">
 * Binary-coded decimal (Wikipedia)
 * </a>
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Bi-quinary_coded_decimal">
 * Bi-quinary coded decimal (Wikipedia)
 * </a>
 */
public class BcdRegister {

    /**
     * Max decimal digits for unsigned int32.
     *
     * <p>(&quot;4294967295&quot;.length)
     */
    public static final int MAX_COL_UINT32 = 10;

    /**
     * Max decimal digits for unsigned int64.
     *
     * <p>(&quot;18446744073709551615&quot;.length)
     */
    public static final int MAX_COL_UINT64 = 20;


    private static final int PRIM_BITSIZE = Integer.SIZE;
    private static final int BYTE_BITSIZE = Byte.SIZE;
    private static final int BCD_BITSIZE = 4;
    private static final int PRIM_SLOTS = PRIM_BITSIZE / BCD_BITSIZE;

    private static final int LSB_PRIMMASK = 0b1;
    private static final int MSB_PRIMMASK = 0b1 << (PRIM_BITSIZE - 1);
    private static final int NIBBLE_MASK  = // 0b1111
            (0b1 << BCD_BITSIZE) - 1;
    private static final int BYTE_MASK    = // 0b1111_1111
            (0b1 << BYTE_BITSIZE) - 1;

    private static final int PRIMIDX_SHIFT = 3;    //  [ /  8]  [>>> 3]
    private static final int NBLIDX_MASK =         //  [mod 8]  [& 0b111]
            (0b1 << PRIMIDX_SHIFT) - 1;

    private static final char SP = '\u0020';
    private static final char CH_OPEN = '[';
    private static final char CH_CLOSE = ']';

    private static final char[] HEXCH_TBL = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F',
    };

    private static final int[] BQ_TBL;


    static{
        // build lookup table for Packed-BCD to Bi-quinary conversion
        BQ_TBL = new int[256];

        int idx = 0;
        for(int highDec = 0; highDec < 16; highDec++){
            int highBq;
            if     (highDec >= 10) highBq = 0x0;
            else if(highDec >=  5) highBq = highDec + 3;
            else                   highBq = highDec;

            for(int lowDec = 0; lowDec < 16; lowDec++){
                int lowBq;
                if     (lowDec >= 10) lowBq = 0x0;
                else if(lowDec >=  5) lowBq = lowDec + 3;
                else                  lowBq = lowDec;

                int bqNblNbl = (highBq << BCD_BITSIZE) | lowBq;

                BQ_TBL[idx++] = bqNblNbl;
            }
        }

        assert idx == BQ_TBL.length;
    }

    static{
        assert 0b1 << PRIMIDX_SHIFT == PRIM_SLOTS;
        assert (-1 & NBLIDX_MASK) == PRIM_SLOTS - 1;
        assert HEXCH_TBL.length == 0b1 << BCD_BITSIZE;
    }


    private final int maxDigits;
    private final int[] ibuf;

    private int precision;    // negative value if unknown.


    /**
     * Constructor.
     *
     * <p>Decimal digits are round-uped to int array fitting size.
     *
     * @param maxDigits decimal digits to store result.
     * @throws IllegalArgumentException not positive digits.
     */
    public BcdRegister(int maxDigits) throws IllegalArgumentException{
        super();

        if(maxDigits <= 0) throw new IllegalArgumentException();

        this.maxDigits = fittingContainer(maxDigits);
        this.ibuf = new int[this.maxDigits / PRIM_SLOTS];

        this.precision = 1;   // Zero-value has precision 1

        return;
    }

    /**
     * Constructor for decimals with unsigned int32.
     */
    public BcdRegister(){
        this(MAX_COL_UINT32);
        return;
    }


    /**
     * Round-up to int array fitting size.
     *
     * @param digits decimal digits
     * @return rounded value
     */
    private static int fittingContainer(int digits){
        int result;

        result = digits;
        result += PRIM_SLOTS - 1;
        result /= PRIM_SLOTS;
        result *= PRIM_SLOTS;

        return result;
    }

    /**
     * Convert each 4bit width PackedBCD to Bi-quinary coded decimal.
     *
     * <p>If each nibble(PackedBCD) in int is greater than 4,
     * add 3 to nibble.
     *
     * <p>"nibble overflow" doesn't occur if valid PackedBCD before.
     * Undefined result if invalid PackedBCD value before.
     *
     * <p>[0,1,2,3,4,5,6,7,8,9] → [0,1,2,3,4,8,9,A,B,C]
     *
     * @param iVal int value
     * @return modified value
     */
    public static int toBiQuinary(int iVal){
        int result;
        int bVal;

        bVal = (iVal >>> 24);
        result = BQ_TBL[bVal];

        bVal = (iVal >>> 16) & BYTE_MASK;
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (iVal >>> 8) & BYTE_MASK;
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = iVal & BYTE_MASK;
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        return result;
    }


    /**
     * Clear all decimal digits to Zero.
     */
    public void clear(){
        int buflen = this.ibuf.length;
        for(int idx = 0; idx < buflen; idx++){
            this.ibuf[idx] = 0;
        }

        this.precision = 1;

        return;
    }

    /**
     * Return total decimal digits holder width on register.
     *
     * <p>Digits holder width include leading zero spaces.
     *
     * @return digits holder width
     */
    public int getMaxDigits(){
        return this.maxDigits;
    }

    /**
     * Get each decimal column value as int.
     *
     * @param digitPos digit column position. 0 is lowest column.
     * @return decimal value from 0 to 9
     * @throws IndexOutOfBoundsException invalid position.
     */
    public int getDigit(int digitPos) throws IndexOutOfBoundsException{
        int iIdx;
        int iMod;

        iIdx = digitPos >>> PRIMIDX_SHIFT;
        //   = digitPos / 8;

        iMod = digitPos & NBLIDX_MASK;
        //   = digitPos % 8;

        int iVal = this.ibuf[iIdx];
        int shiftWidth = iMod << 2;
        //             = iMod * 4;

        iVal >>>= shiftWidth;
        iVal &= NIBBLE_MASK;

        return iVal;
    }

    /**
     * Store decimal result to int array.
     *
     * <p>Each decimal number stored to
     * each int array element. (non-packing BCD)
     *
     * <p>Leading zeros aren't transfered.
     * You need at least precision-size elements space to store.
     *
     * <p>Higher decimal number stored to lower array element.
     * If decimal result is '36', [0] is 3 and [1] is 6.
     *
     * @param dst destination array
     * @param offset start offset
     * @return Stored element size. ( = precision)
     * @throws IndexOutOfBoundsException too small array or negative offset.
     */
    public int toIntArray(int[] dst, int offset)
            throws IndexOutOfBoundsException{
        int digitsCt = getPrecision();
        int addPos = digitsCt - 1;

        for(int iVal : this.ibuf){
            for(int nblIdx = 0; nblIdx < PRIM_SLOTS; nblIdx++){
                int shPos = nblIdx << 2;
                //        = nblIdx * 4;

                dst[offset + addPos] = (iVal >>> shPos) & NIBBLE_MASK;

                if(addPos <= 0) break;

                addPos--;
            }

            if(addPos <= 0) break;
        }

        return digitsCt;
    }

    /**
     * Push 1bit LSB and double-dabble left shift.
     *
     * <p>If decimal overflow happens, just losing higher digits.
     *
     * @param carryOver LSB is 1 if true.
     * @return true if MSB overflow
     */
    public boolean pushLsb(boolean carryOver){
        int pushInt;
        if(carryOver) pushInt = MSB_PRIMMASK;
        else          pushInt = 0;

        boolean result = pushLsb(pushInt);

        return result;
    }

    /**
     * Push 1bit LSB and double-dabble left shift.
     *
     * <p>If decimal overflow happens, just losing higher digits.
     *
     * @param carryOver LSB is 1 if non-zero.
     * @return true if MSB overflow
     */
    public boolean pushLsb(int carryOver){
        int lastMsbTest = carryOver;

        int buflen = this.ibuf.length;
        for(int idx = 0; idx < buflen; idx++){
            int oldVal = this.ibuf[idx];

            int fixVal = toBiQuinary(oldVal);
            int newVal = fixVal << 1;
            if(lastMsbTest != 0) newVal |= LSB_PRIMMASK;

            this.ibuf[idx] = newVal;
            lastMsbTest = fixVal & MSB_PRIMMASK;
        }

        this.precision = -1;   // unknown precision
        boolean result =
                lastMsbTest != 0;

        return result;
    }

    /**
     * Return decimal precision.
     *
     * <p>Precision is a valid decimal digits width without leading zero.
     *
     * <p>Precision is less or equal than max digits size always.
     *
     * <p>Precision of '40560' is 5.
     *
     * <p>Precision of '0' is 1.
     *
     * @return decimal precision.
     */
    public int getPrecision(){
        if(this.precision > 0){
            return this.precision;
        }

        int result = calcPrecision();
        if(result == 0) result = 1;

        this.precision = result;

        return result;
    }

    /**
     * Calculate decimal precision.
     *
     * <p>Precision is valid decimal digits without leading zero.
     *
     * <p>Precision of 40560 is 5.
     *
     * <p>Precision of 0 is 1 but return 0.
     *
     * @return decimal precision.
     */
    private int calcPrecision(){
        int result = this.maxDigits;

        int idxMax = this.ibuf.length - 1;
        for(int iIdx = idxMax; iIdx >= 0; iIdx--){
            int iVal = this.ibuf[iIdx];

            if(iVal == 0){
                result -= PRIM_SLOTS;
            }else{
                // Count Leading Zero nibbles(BCD)
                if((iVal & 0xff_ff_00_00) == 0){
                    result -= 4;
                    iVal <<= 16;
                }
                if((iVal & 0xff_00_00_00) == 0){
                    result -= 2;
                    iVal <<= 8;
                }
                if((iVal & 0xf0_00_00_00) == 0){
                    result -= 1;
                }

                break;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     * Return text for debug usage.
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        boolean dumped = false;
        int validCols = getPrecision();
        for(int colCt = validCols - 1; colCt >= 0; colCt--){
            int iIdx   = colCt >>> PRIMIDX_SHIFT;
            //         = colCt / 8;
            int nblIdx = colCt & NBLIDX_MASK;
            //         = colCt % 8;

            int iVal = this.ibuf[iIdx];
            int shiftWidth = nblIdx << 2;
            //             = nblIdx * 4;

            iVal >>>= shiftWidth;
            int nibble = iVal & NIBBLE_MASK;

            if(dumped){
                sb.append(SP);
            }

            dumpNibble(sb, nibble);
            dumped = true;
        }

        String result = sb.toString();
        return result;
    }

    /**
     * Dump nibble.
     *
     * <p>nibble 5 -> 0101[5]
     *
     * @param sb output
     * @param nibble BCD nibble value. (0 =&lt; nibble &lt; 10)
     */
    private void dumpNibble(StringBuilder sb, int nibble){
        int b3 = (nibble >> 3) & LSB_PRIMMASK;
        int b2 = (nibble >> 2) & LSB_PRIMMASK;
        int b1 = (nibble >> 1) & LSB_PRIMMASK;
        int b0 = (nibble     ) & LSB_PRIMMASK;

        char c3 = HEXCH_TBL[b3];
        char c2 = HEXCH_TBL[b2];
        char c1 = HEXCH_TBL[b1];
        char c0 = HEXCH_TBL[b0];

        sb.append(c3);
        sb.append(c2);
        sb.append(c1);
        sb.append(c0);

        assert nibble < 16;
        char decCh = HEXCH_TBL[nibble];

        sb.append(CH_OPEN);
        sb.append(decCh);
        sb.append(CH_CLOSE);

        return;
    }

}
