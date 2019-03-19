/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

/**
 * Utilities for Packed BCD layouted on primitive value.
 *
 * <p>Each decimal columns are layouted 4bit nibble width
 * on primitive value.
 *
 * <p>Lower decimal digit is layouted on lower bits.
 *
 * <p>Each decimal digit overlaps Packed-BCD and Bi-quinary coded decimal.
 *
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Binary-coded_decimal">
 * Binary-coded decimal (Wikipedia)
 * </a>
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Bi-quinary_coded_decimal">
 * Bi-quinary coded decimal (Wikipedia)
 * </a>
 */
public final class BcdUtils {

    /** bit-size of BCD(nibble). */
    public static final int BCD_BITSIZE = 4;

    private static final int BYTE_BITSIZE = Byte.SIZE;
    private static final int BYTE_MASK    = // 0b1111_1111
            (0b1 << BYTE_BITSIZE) - 1;

    private static final int INT_SLOTS  = Integer.SIZE / BCD_BITSIZE;
    private static final int LONG_SLOTS = Long.SIZE    / BCD_BITSIZE;

    private static final int[] BQ_TBL;

    static{
        // build lookup table for Packed-BCD to Bi-quinary conversion
        BQ_TBL = new int[256];

        int[] bqline = new int[]{
            0x00, 0x01, 0x02, 0x03, 0x04,
            0x08, 0x09, 0x0a, 0x0b, 0x0c,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        int idx = 0;
        for(int highDec : bqline){
            int highSft = highDec << BCD_BITSIZE;
            for(int lowDec : bqline){
                int bqNblNbl = highSft | lowDec;
                BQ_TBL[idx++] = bqNblNbl;
            }
        }

        assert idx == BQ_TBL.length;
    }


    /**
     * Hiden constructor.
     */
    private BcdUtils(){
        assert false;
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
        int idx3 = iVal >>> 24;
        int idx2 = iVal >>> 16 & BYTE_MASK;
        int idx1 = iVal >>>  8 & BYTE_MASK;
        int idx0 = iVal        & BYTE_MASK;

        int b3 = BQ_TBL[idx3];
        int b2 = BQ_TBL[idx2];
        int b1 = BQ_TBL[idx1];
        int b0 = BQ_TBL[idx0];

        int result =
                  ((b3 << BYTE_BITSIZE) | b2) << 16
                | ((b1 << BYTE_BITSIZE) | b0);

        return result;
    }

    /**
     * Convert each 4bit width PackedBCD to Bi-quinary coded decimal.
     *
     * <p>If each nibble(PackedBCD) in long is greater than 4,
     * add 3 to nibble.
     *
     * <p>"nibble overflow" doesn't occur if valid PackedBCD before.
     * Undefined result if invalid PackedBCD value before.
     *
     * <p>[0,1,2,3,4,5,6,7,8,9] → [0,1,2,3,4,8,9,A,B,C]
     *
     * @param lVal long value
     * @return modified value
     */
    public static long toBiQuinary(long lVal) {
        long result;

        int bVal;

        bVal = (int) (lVal >>> 56);
        result = BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 48) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 40) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 32) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 24) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 16) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) ((lVal >>> 8) & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        bVal = (int) (lVal & BYTE_MASK);
        result <<= BYTE_BITSIZE;
        result |= BQ_TBL[bVal];

        return result;
    }

    /**
     * Count Leading Zero nibbles.
     *
     * <ul>
     * <li>Return 0 if 0xffffffff.
     * <li>Return 0 if 0x1fffffff.
     * <li>Return 3 if 0x000fffff.
     * <li>Return 5 if 0x00000100.
     * <li>Return 7 if 0x0000000f.
     * <li>Return 7 if 0x00000001.
     * <li>Return 8 if 0x00000000.
     * </ul>
     *
     * @param iVal int value
     * @return Zero nibbles
     */
    public static int clzNibble(int iVal){
        if(iVal == 0){
            return INT_SLOTS;
        }

        int b2;
        if((iVal & 0xff_ff_00_00) == 0){
            b2 = 0b0100;
            iVal <<= 16;
        }else{
            b2 = 0b0000;
        }

        int b1;
        if((iVal & 0xff_00_00_00) == 0){
            b1 = 0b0010;
            iVal <<= 8;
        }else{
            b1 = 0b0000;
        }

        int b0;
        if((iVal & 0xf0_00_00_00) == 0){
            b0 = 0b0001;
        }else{
            b0 = 0b0000;
        }

        return b2 | b1 | b0;
    }

    /**
     * Count Leading Zero nibbles.
     *
     * <ul>
     * <li>Return  0 if 0xffffffffffffffff.
     * <li>Return  0 if 0x1fffffffffffffff.
     * <li>Return  3 if 0x000fffffffffffff.
     * <li>Return  5 if 0x0000010000000000.
     * <li>Return 15 if 0x000000000000000f.
     * <li>Return 15 if 0x0000000000000001.
     * <li>Return 16 if 0x0000000000000000.
     * </ul>
     *
     * @param lVal long value
     * @return Zero nibbles
     */
    public static int clzNibble(long lVal){
        if(lVal == 0){
            return LONG_SLOTS;
        }

        int b3;
        if((lVal & 0xff_ff_ff_ff_00_00_00_00L) == 0){
            b3 = 0b1000;
            lVal <<= 32;
        }else{
            b3 = 0b0000;
        }

        int b2;
        if((lVal & 0xff_ff_00_00_00_00_00_00L) == 0){
            b2 = 0b0100;
            lVal <<= 16;
        }else{
            b2 = 0b0000;
        }

        int b1;
        if((lVal & 0xff_00_00_00_00_00_00_00L) == 0){
            b1 = 0b0010;
            lVal <<= 8;
        }else{
            b1 = 0b0000;
        }

        int b0;
        if((lVal & 0xf0_00_00_00_00_00_00_00L) == 0){
            b0 = 0b0001;
        }else{
            b0 = 0b0000;
        }

        return (b3 | b2) | (b1 | b0);
    }

}
