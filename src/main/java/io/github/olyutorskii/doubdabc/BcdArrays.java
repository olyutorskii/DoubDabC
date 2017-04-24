/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

/**
 * BCD array utilities but experimental.
 */
public final class BcdArrays {

    private static final int INT_SLOTS =
            Integer.SIZE / BcdUtils.BCD_BITSIZE;

    private static final int MSB_INTMASK = 0b1  << (Integer.SIZE - 1);
    private static final int LSB_INTMASK = 0b1;

    private static final int BCD_MASK =
            (0b1 << BcdUtils.BCD_BITSIZE) - 1;
    private static final int ARABICUC_MASK = 0b0011_0000;


    /**
     * Hidden constructor.
     */
    private BcdArrays(){
        assert false;
    }


    /**
     * Convert int value to Arabic-decimal character sequence.
     *
     * <p>Output nothing if value 0.
     *
     * <p>sign-bit is treated as unsigned.
     * There is no minus-sign output.
     *
     * <p>DDA implementations without any instance field.
     *
     * @param iVal int value
     * @param cbuf char output
     * @param offset output start offset
     * @return output length
     */
    public static int int2ArabicArray(final int iVal,
                                        final char[] cbuf, final int offset){
        int iHigh = 0b0;
        int iLow  = 0b0;

        boolean skipLeading = true;
        boolean noHalfCarry = true;
        int sig = 0;

        for(int bitCt = 0; bitCt < Integer.SIZE; bitCt++){
            final int scanMask = MSB_INTMASK >>> bitCt;
            final int bitAppear = iVal & scanMask;

            if(skipLeading){
                if(bitAppear == 0b0) continue;
                skipLeading = false;
            }
            sig++;

            int bqVal;
            int shiftedBcd;

            bqVal = BcdUtils.toBiQuinary(iLow);
            shiftedBcd = bqVal << 1;

            if(bitAppear == 0b0){
                iLow = shiftedBcd;
            }else{
                iLow = shiftedBcd | LSB_INTMASK;
            }

            // through iHigh
            // if iLow has 8-bcd "67108863"(2^26-1) or lower
            if(sig < 27) continue;

            int halfCarry = bqVal & MSB_INTMASK;

            if(noHalfCarry){
                if(halfCarry == 0b0) continue;
                noHalfCarry = false;
            }

            bqVal = BcdUtils.toBiQuinary(iHigh);
            shiftedBcd = bqVal << 1;

            if(halfCarry == 0b0){
                iHigh = shiftedBcd;
            }else{
                iHigh = shiftedBcd | LSB_INTMASK;
            }
        }

        int cidx = offset;
        final int clzLow;

        if(iHigh == 0b0){
            clzLow = BcdUtils.clzNibble(iLow);
        }else{
            clzLow = 0;

            final int clzHigh = BcdUtils.clzNibble(iHigh);

            for(int bcdCt = clzHigh; bcdCt < INT_SLOTS; bcdCt++){
                final int nibble = (iHigh >>> ((7 - bcdCt) << 2)) & BCD_MASK;
                final char decimalCh = (char)(nibble | ARABICUC_MASK);
                cbuf[cidx++] = decimalCh;
            }
        }

        for(int bcdCt = clzLow; bcdCt < INT_SLOTS; bcdCt++){
            final int nibble = (iLow >>> ((7 - bcdCt) << 2)) & BCD_MASK;
            final char decimalCh = (char)(nibble | ARABICUC_MASK);
            cbuf[cidx++] = decimalCh;
        }

        return cidx - offset;
    }

}
