/*
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
 * @see <a href="https://en.wikipedia.org/wiki/Double_dabble">
 * Double dabble (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Binary-coded_decimal">
 * Binary-coded decimal (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Bi-quinary_coded_decimal">
 * Bi-quinary coded decimal (Wikipedia)
 * </a>
 */
public class BcdRegister {

    /**
     * Max decimal digits for unsigned int32.
     *
     * <p>(&quot;4294967295&quot;.length)
     */
    public static final int MAX_COL_UINT32 = 10; //   4294967295

    /**
     * Max decimal digits for unsigned int64.
     *
     * <p>(&quot;18446744073709551615&quot;.length)
     */
    public static final int MAX_COL_UINT64 = 20; //   18446744073709551615
                                                 // ->12345678901234567890<-


    private static final int PRIM_BITSIZE = Integer.SIZE;
    private static final int BCD_BITSIZE = 4;
    private static final int PRIM_SLOTS = PRIM_BITSIZE / BCD_BITSIZE;

    private static final int LSB_PRIMMASK = 0b1;
    private static final int MSB_PRIMMASK = 0b1 << (PRIM_BITSIZE - 1);
    private static final int NIBBLE_MASK = // 0b1111
            (0b1 << BCD_BITSIZE) - 1;

    private static final int PRIMIDX_SHIFT = 3;    //  [ /  8]  [>>> 3]
    private static final int NBLIDX_MASK =         //  [mod 8]  [& 0b111]
            (0b1 << PRIMIDX_SHIFT) - 1;

    private static final char SP = '\u0020';
    private static final char CH_OPEN = '[';
    private static final char CH_CLOSE = ']';

    private static final int[] NIBBLE_IDX;     // {0,4,8...,24}
    private static final char[] HEXCH_TBL = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F',
    };

    static{
        NIBBLE_IDX = new int[PRIM_SLOTS];
        for(int ct = 0; ct < PRIM_SLOTS; ct++){
            NIBBLE_IDX[ct] = BCD_BITSIZE * ct;
        }
    }

    static{
        assert 0b1 << PRIMIDX_SHIFT == PRIM_SLOTS;
        assert NIBBLE_IDX.length == PRIM_SLOTS;
        assert HEXCH_TBL.length == 0b1 << BCD_BITSIZE;
    }


    private final int digits;
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

        this.digits = fittingContainer(maxDigits);
        this.ibuf = new int[this.digits / PRIM_SLOTS];

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
        int result = iVal;

        for(int nibblePos : NIBBLE_IDX){
            int bcd = (iVal >>> nibblePos) & NIBBLE_MASK;
            if(bcd > 0x4){
                int three = 0x3 << nibblePos;
                result += three;    // never carry over between nibbles
            }
        }

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
        return this.digits;
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

        iIdx = digitPos >> PRIMIDX_SHIFT;
        //   = digitPos / 8;

        iMod = digitPos & NBLIDX_MASK;
        //   = digitPos % 8;

        int iVal = this.ibuf[iIdx];
        int shiftWidth = NIBBLE_IDX[iMod];
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

        int buflen = this.ibuf.length;
        outer:
        for(int iIdx = 0; iIdx < buflen; iIdx++){
            int iVal = this.ibuf[iIdx];

            int lastNblIdx = NIBBLE_IDX.length - 1;
            for(int nblIdx = 0; nblIdx <= lastNblIdx; nblIdx++){
                int shPos = NIBBLE_IDX[nblIdx];
                int shifted = iVal >>> shPos;
                int maskedValue = shifted & NIBBLE_MASK;

                int dstPos = offset + addPos;
                dst[dstPos] = maskedValue;

                if(addPos > 0) addPos--;
                else           break outer;
            }
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
        int result = this.digits;

        int idxMax = this.ibuf.length - 1;
        outer:
        for(int iIdx = idxMax; iIdx >= 0; iIdx--){
            int iVal = this.ibuf[iIdx];

            if(iVal == 0){
                result -= PRIM_SLOTS;
                continue;
            }

            int lastNblIdx = NIBBLE_IDX.length - 1;
            for(int nblIdx = lastNblIdx; nblIdx >= 0; nblIdx--){
                int shPos = NIBBLE_IDX[nblIdx];
                int nibbleMask = NIBBLE_MASK << shPos;
                int maskedValue = iVal & nibbleMask;

                if(maskedValue != 0){
                    break outer;
                }

                result--;
            }

            assert false;
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
            int iIdx   = colCt >>> PRIMIDX_SHIFT;   // / PRIM_SLOTS;
            int nblIdx = colCt & NBLIDX_MASK;       // % PRIM_SLOTS;

            int iVal = this.ibuf[iIdx];
            int shPos = NIBBLE_IDX[nblIdx];

            int nibbleMask = NIBBLE_MASK << shPos;
            int maskedValue = iVal & nibbleMask;
            int nibble = maskedValue >>> shPos;

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
        for(int bitIdx = BCD_BITSIZE - 1; bitIdx >= 0; bitIdx--){
            int bMask = LSB_PRIMMASK << bitIdx;
            int bool = nibble & bMask;

            char boolCh;
            if(bool == 0) boolCh = HEXCH_TBL[0];
            else          boolCh = HEXCH_TBL[1];
            sb.append(boolCh);
        }

        assert nibble < 10;
        char decCh = HEXCH_TBL[nibble];
        sb.append(CH_OPEN);
        sb.append(decCh);
        sb.append(CH_CLOSE);

        return;
    }

}
