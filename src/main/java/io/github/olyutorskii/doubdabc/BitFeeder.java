/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import java.math.BigInteger;
import java.util.BitSet;

/**
 * Bit-stream feeder from any integer to BCD register.
 *
 * <p>Any value on register was cleared before feeding.
 */
public final class BitFeeder {

    private static final int  MSB_INTMASK  = 0b1  << (Integer.SIZE - 1);
    private static final long MSB_LONGMASK = 0b1L << (Long.SIZE    - 1);


    /**
     * Hidden constructor.
     */
    private BitFeeder() {
        assert false;
    }


    /**
     * Feed 32bit integer
     * from higher to lower bit with double-dabble left shift.
     *
     * <p>sign-bit is treated as unsigned.
     *
     * @param bs BCD register
     * @param iVal 32bit integer
     * @return true if MSB overflow
     */
    public static boolean feedUInt32(BcdRegister bs, int iVal) {
        bs.clear();

        boolean skipping = true;
        boolean result = false;

        for (int ct = 0; ct < Integer.SIZE; ct++) {
            int scanMask = MSB_INTMASK >>> ct;
            int bitScanned = iVal & scanMask;

            if (skipping) {
                if (bitScanned == 0b0) continue;
                skipping = false;
            }

            result |= bs.pushLsb(bitScanned);
        }
        return result;
    }

    /**
     * Feed unsigned 64bit integer
     * from higher to lower bit with double-dabble left shift.
     *
     * <p>sign-bit is treated as unsigned.
     *
     * @param bs BCD register
     * @param lVal 64bit integer
     * @return true if MSB overflow
     */
    public static boolean feedUInt64(BcdRegister bs, long lVal) {
        bs.clear();

        boolean skipping = true;
        boolean result = false;

        for (int ct = 0; ct < Long.SIZE; ct++) {
            long scanMask = MSB_LONGMASK >>> ct;
            long bitScanned = lVal & scanMask;

            if (skipping) {
                if (bitScanned == 0b0L) continue;
                skipping = false;
            }

            int newLsbInt;
            if (bitScanned == 0L) {
                newLsbInt = 0;
            } else {
                newLsbInt = 1;
            }

            result |= bs.pushLsb(newLsbInt);
        }

        return result;
    }

    /**
     * Feed BitInteger
     * from higher to lower bit with double-dabble left shift.
     *
     * <p>sign-bit is treated as unsigned.
     *
     * @param bs BCD register
     * @param bigInt big integer
     * @return true if MSB overflow
     */
    public static boolean feedBigInteger(BcdRegister bs, BigInteger bigInt) {
        bs.clear();

        boolean result = false;

        int sign = bigInt.signum();
        if (sign < 0) result |= bs.pushLsb(true);

        int msbPos = bigInt.bitLength() - 1;

        for (int bitIndex = msbPos; bitIndex >= 0; bitIndex--) {
            boolean newLsb = bigInt.testBit(bitIndex);
            result |= bs.pushLsb(newLsb);
        }

        return result;
    }

    /**
     * Feed BitSet
     * from higher to lower bit with double-dabble left shift.
     *
     * @param bs BCD register
     * @param bitSet bit set
     * @return true if MSB overflow
     */
    public static boolean feedBitSet(BcdRegister bs, BitSet bitSet) {
        bs.clear();

        int msbPos = bitSet.length() - 1;

        boolean result = false;

        for (int bitIndex = msbPos; bitIndex >= 0; bitIndex--) {
            boolean newLsb = bitSet.get(bitIndex);
            result |= bs.pushLsb(newLsb);
        }

        return result;
    }

}
