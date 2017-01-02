/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import java.math.BigInteger;
import java.util.BitSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BitFeederTest {

    public BitFeederTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of feedUInt32 method, of class BitFeeder.
     */
    @Test
    public void testFeedUInt32() {
        System.out.println("feedUInt32");

        BcdRegister bs;
        boolean overflow;

        bs = new BcdRegister(BcdRegister.MAX_COL_UINT32);

        overflow = BitFeeder.feedUInt32(bs, 243);
        assertFalse(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, Integer.MAX_VALUE);
        assertFalse(overflow);
        assertEquals(10, bs.getPrecision());
        assertEquals(2, bs.getDigit(9));
        assertEquals(1, bs.getDigit(8));
        assertEquals(4, bs.getDigit(7));
        assertEquals(7, bs.getDigit(6));
        assertEquals(4, bs.getDigit(5));
        assertEquals(8, bs.getDigit(4));
        assertEquals(3, bs.getDigit(3));
        assertEquals(6, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(7, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, Integer.MIN_VALUE);
        assertFalse(overflow);
        assertEquals(10, bs.getPrecision());
        assertEquals(2, bs.getDigit(9));
        assertEquals(1, bs.getDigit(8));
        assertEquals(4, bs.getDigit(7));
        assertEquals(7, bs.getDigit(6));
        assertEquals(4, bs.getDigit(5));
        assertEquals(8, bs.getDigit(4));
        assertEquals(3, bs.getDigit(3));
        assertEquals(6, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(8, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, -1);
        assertFalse(overflow);
        assertEquals(10, bs.getPrecision());
        assertEquals(4, bs.getDigit(9));
        assertEquals(2, bs.getDigit(8));
        assertEquals(9, bs.getDigit(7));
        assertEquals(4, bs.getDigit(6));
        assertEquals(9, bs.getDigit(5));
        assertEquals(6, bs.getDigit(4));
        assertEquals(7, bs.getDigit(3));
        assertEquals(2, bs.getDigit(2));
        assertEquals(9, bs.getDigit(1));
        assertEquals(5, bs.getDigit(0));

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        overflow = BitFeeder.feedUInt32(bs, 9999_9999);
        assertFalse(overflow);
        assertEquals(8, bs.getPrecision());
        assertEquals(9, bs.getDigit(7));
        assertEquals(9, bs.getDigit(6));
        assertEquals(9, bs.getDigit(5));
        assertEquals(9, bs.getDigit(4));
        assertEquals(9, bs.getDigit(3));
        assertEquals(9, bs.getDigit(2));
        assertEquals(9, bs.getDigit(1));
        assertEquals(9, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, 1_0000_0000);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, 9_0000_0000);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, 1_0000_0243);
        assertTrue(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        overflow = BitFeeder.feedUInt32(bs, 20_0000_0000);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        return;
    }

    /**
     * Test of feedUInt64 method, of class BitFeeder.
     */
    @Test
    public void testFeedUInt64() {
        System.out.println("feedUInt64");

        BcdRegister bs;
        boolean overflow;

        bs = new BcdRegister(BcdRegister.MAX_COL_UINT64);

        overflow = BitFeeder.feedUInt64(bs, 243L);
        assertFalse(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, Long.MAX_VALUE);
        assertFalse(overflow);
        assertEquals(19, bs.getPrecision());
        assertEquals(9, bs.getDigit(18));
        assertEquals(2, bs.getDigit(17));
        assertEquals(2, bs.getDigit(16));
        assertEquals(3, bs.getDigit(15));
        assertEquals(3, bs.getDigit(14));
        assertEquals(7, bs.getDigit(13));
        assertEquals(2, bs.getDigit(12));
        assertEquals(0, bs.getDigit(11));
        assertEquals(3, bs.getDigit(10));
        assertEquals(6, bs.getDigit(9));
        assertEquals(8, bs.getDigit(8));
        assertEquals(5, bs.getDigit(7));
        assertEquals(4, bs.getDigit(6));
        assertEquals(7, bs.getDigit(5));
        assertEquals(7, bs.getDigit(4));
        assertEquals(5, bs.getDigit(3));
        assertEquals(8, bs.getDigit(2));
        assertEquals(0, bs.getDigit(1));
        assertEquals(7, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, Long.MIN_VALUE);
        assertFalse(overflow);
        assertEquals(19, bs.getPrecision());
        assertEquals(9, bs.getDigit(18));
        assertEquals(2, bs.getDigit(17));
        assertEquals(2, bs.getDigit(16));
        assertEquals(3, bs.getDigit(15));
        assertEquals(3, bs.getDigit(14));
        assertEquals(7, bs.getDigit(13));
        assertEquals(2, bs.getDigit(12));
        assertEquals(0, bs.getDigit(11));
        assertEquals(3, bs.getDigit(10));
        assertEquals(6, bs.getDigit(9));
        assertEquals(8, bs.getDigit(8));
        assertEquals(5, bs.getDigit(7));
        assertEquals(4, bs.getDigit(6));
        assertEquals(7, bs.getDigit(5));
        assertEquals(7, bs.getDigit(4));
        assertEquals(5, bs.getDigit(3));
        assertEquals(8, bs.getDigit(2));
        assertEquals(0, bs.getDigit(1));
        assertEquals(8, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, -1L);
        assertFalse(overflow);
        assertEquals(20, bs.getPrecision());
        assertEquals(1, bs.getDigit(19));
        assertEquals(8, bs.getDigit(18));
        assertEquals(4, bs.getDigit(17));
        assertEquals(4, bs.getDigit(16));
        assertEquals(6, bs.getDigit(15));
        assertEquals(7, bs.getDigit(14));
        assertEquals(4, bs.getDigit(13));
        assertEquals(4, bs.getDigit(12));
        assertEquals(0, bs.getDigit(11));
        assertEquals(7, bs.getDigit(10));
        assertEquals(3, bs.getDigit(9));
        assertEquals(7, bs.getDigit(8));
        assertEquals(0, bs.getDigit(7));
        assertEquals(9, bs.getDigit(6));
        assertEquals(5, bs.getDigit(5));
        assertEquals(5, bs.getDigit(4));
        assertEquals(1, bs.getDigit(3));
        assertEquals(6, bs.getDigit(2));
        assertEquals(1, bs.getDigit(1));
        assertEquals(5, bs.getDigit(0));

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        overflow = BitFeeder.feedUInt64(bs, 9999_9999L);
        assertFalse(overflow);
        assertEquals(8, bs.getPrecision());
        assertEquals(9, bs.getDigit(7));
        assertEquals(9, bs.getDigit(6));
        assertEquals(9, bs.getDigit(5));
        assertEquals(9, bs.getDigit(4));
        assertEquals(9, bs.getDigit(3));
        assertEquals(9, bs.getDigit(2));
        assertEquals(9, bs.getDigit(1));
        assertEquals(9, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, 1_0000_0000L);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, 9_0000_0000L);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, 1_0000_0243L);
        assertTrue(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        overflow = BitFeeder.feedUInt64(bs, 1000_0000_0000L);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        return;
    }

    /**
     * Test of feedBigInteger method, of class BitFeeder.
     */
    @Test
    public void testFeedBigInteger() {
        System.out.println("feedBigInteger");

        BcdRegister bs;
        BigInteger bigInt;
        boolean overflow;

        bs = new BcdRegister();

        bigInt = BigInteger.valueOf(243L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        bigInt = BigInteger.valueOf(2L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(2, bs.getDigit(0));

        bigInt = BigInteger.valueOf(1L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(1, bs.getDigit(0));

        bigInt = BigInteger.valueOf(0L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        // 0b1
        bigInt = BigInteger.valueOf(-1L);
        assertEquals(0, bigInt.bitLength());
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(1, bs.getDigit(0));

        // 0b10
        bigInt = BigInteger.valueOf(-2L);
        assertEquals(1, bigInt.bitLength());
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(2, bs.getDigit(0));

        // 0b101
        bigInt = BigInteger.valueOf(-3L);
        assertEquals(2, bigInt.bitLength());
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(5, bs.getDigit(0));

        // 0b100
        bigInt = BigInteger.valueOf(-4L);
        assertEquals(2, bigInt.bitLength());
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(4, bs.getDigit(0));

        // 0b1011
        bigInt = BigInteger.valueOf(-5L);
        assertEquals(3, bigInt.bitLength());
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(2, bs.getPrecision());
        assertEquals(1, bs.getDigit(1));
        assertEquals(1, bs.getDigit(0));

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        bigInt = BigInteger.valueOf(9999_9999L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertFalse(overflow);
        assertEquals(8, bs.getPrecision());
        assertEquals(9, bs.getDigit(7));
        assertEquals(9, bs.getDigit(6));
        assertEquals(9, bs.getDigit(5));
        assertEquals(9, bs.getDigit(4));
        assertEquals(9, bs.getDigit(3));
        assertEquals(9, bs.getDigit(2));
        assertEquals(9, bs.getDigit(1));
        assertEquals(9, bs.getDigit(0));

        bigInt = BigInteger.valueOf(1_0000_0000L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        bigInt = BigInteger.valueOf(9_0000_0000L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        bigInt = BigInteger.valueOf(1_0000_0243L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertTrue(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        bigInt = BigInteger.valueOf(1000_0000_0000L);
        overflow = BitFeeder.feedBigInteger(bs, bigInt);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        return;
    }

    /**
     * Test of feedBitSet method, of class BitFeeder.
     */
    @Test
    public void testFeedBitSet() {
        System.out.println("feedBitSet");

        BcdRegister bs;
        BitSet set;
        boolean overflow;

        bs = new BcdRegister(3);
        set = new BitSet();

        overflow = BitFeeder.feedBitSet(bs, set);
        assertFalse(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        set.set(0, true);
        set.set(1, true);
        set.set(2, false);
        set.set(3, false);
        set.set(4, true);
        set.set(5, true);
        set.set(6, true);
        set.set(7, true);

        overflow = BitFeeder.feedBitSet(bs, set);
        assertFalse(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        set.set(100, false);

        overflow = BitFeeder.feedBitSet(bs, set);
        assertFalse(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        set = BitSet.valueOf(new long[]{9999_9999L});
        overflow = BitFeeder.feedBitSet(bs, set);
        assertFalse(overflow);
        assertEquals(8, bs.getPrecision());
        assertEquals(9, bs.getDigit(7));
        assertEquals(9, bs.getDigit(6));
        assertEquals(9, bs.getDigit(5));
        assertEquals(9, bs.getDigit(4));
        assertEquals(9, bs.getDigit(3));
        assertEquals(9, bs.getDigit(2));
        assertEquals(9, bs.getDigit(1));
        assertEquals(9, bs.getDigit(0));

        set = BitSet.valueOf(new long[]{1_0000_0000L});
        overflow = BitFeeder.feedBitSet(bs, set);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        set = BitSet.valueOf(new long[]{9_0000_0000L});
        overflow = BitFeeder.feedBitSet(bs, set);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        set = BitSet.valueOf(new long[]{1_0000_0243L});
        overflow = BitFeeder.feedBitSet(bs, set);
        assertTrue(overflow);
        assertEquals(3, bs.getPrecision());
        assertEquals(2, bs.getDigit(2));
        assertEquals(4, bs.getDigit(1));
        assertEquals(3, bs.getDigit(0));

        set = BitSet.valueOf(new long[]{1000_0000_0000L});
        overflow = BitFeeder.feedBitSet(bs, set);
        assertTrue(overflow);
        assertEquals(1, bs.getPrecision());
        assertEquals(0, bs.getDigit(0));

        return;
    }

}
