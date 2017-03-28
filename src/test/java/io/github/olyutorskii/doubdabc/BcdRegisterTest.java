/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BcdRegisterTest {

    public BcdRegisterTest() {
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
     * Test of constructor, of class BcdRegister.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        BcdRegister bs;

        try{
            bs = new BcdRegister(0);
            fail();
        }catch(IllegalArgumentException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of clear method, of class BcdRegister.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        return;
    }

    /**
     * Test of getDigit method, of class BcdRegister.
     */
    @Test
    public void testGetDigit() {
        System.out.println("getDigit");

        BcdRegister bs;

        bs = new BcdRegister(8);

        assertEquals(0, bs.getDigit(0));
        assertEquals(0, bs.getDigit(1));
        assertEquals(0, bs.getDigit(7));

        try{
            bs.getDigit(8);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        try{
            bs.getDigit(-1);
            fail();
        }catch(IndexOutOfBoundsException e){
        }

        return;
    }

    /**
     * Test of toBiQuinary method, of class BcdRegister.
     */
    @Test
    public void testToBiQuinary_int() {
        System.out.println("toBiQuinary");

        int result;

        result = BcdRegister.toBiQuinary(0x00);
        assertEquals(0x00, result);
        result = BcdRegister.toBiQuinary(0x01);
        assertEquals(0x01, result);
        result = BcdRegister.toBiQuinary(0x02);
        assertEquals(0x02, result);
        result = BcdRegister.toBiQuinary(0x03);
        assertEquals(0x03, result);
        result = BcdRegister.toBiQuinary(0x04);
        assertEquals(0x04, result);
        result = BcdRegister.toBiQuinary(0x05);
        assertEquals(0x08, result);
        result = BcdRegister.toBiQuinary(0x06);
        assertEquals(0x09, result);
        result = BcdRegister.toBiQuinary(0x07);
        assertEquals(0x0a, result);
        result = BcdRegister.toBiQuinary(0x08);
        assertEquals(0x0b, result);
        result = BcdRegister.toBiQuinary(0x09);
        assertEquals(0x0c, result);

        result = BcdRegister.toBiQuinary(0x40);
        assertEquals(0x40, result);
        result = BcdRegister.toBiQuinary(0x50);
        assertEquals(0x80, result);

        result = BcdRegister.toBiQuinary(0x400);
        assertEquals(0x400, result);
        result = BcdRegister.toBiQuinary(0x500);
        assertEquals(0x800, result);

        result = BcdRegister.toBiQuinary(0x4000);
        assertEquals(0x4000, result);
        result = BcdRegister.toBiQuinary(0x5000);
        assertEquals(0x8000, result);

        result = BcdRegister.toBiQuinary(0x9999);
        assertEquals(0xcccc, result);

        result = BcdRegister.toBiQuinary(0x3456);
        assertEquals(0x3489, result);

        result = BcdRegister.toBiQuinary(0x40_00_00_00);
        assertEquals(0x40_00_00_00, result);

        result = BcdRegister.toBiQuinary(0x50_00_00_00);
        assertEquals(0x80_00_00_00, result);

        result = BcdRegister.toBiQuinary(0x60_00_00_00);
        assertEquals(0x90_00_00_00, result);

        result = BcdRegister.toBiQuinary(0x70_00_00_00);
        assertEquals(0xa0_00_00_00, result);

        result = BcdRegister.toBiQuinary(0x80_00_00_00);
        assertEquals(0xb0_00_00_00, result);

        result = BcdRegister.toBiQuinary(0x90_00_00_00);
        assertEquals(0xc0_00_00_00, result);

        return;
    }

    /**
     * Test of toBiQuinary method, of class BcdRegister.
     */
    @Test
    public void testToBiQuinary_long() {
        System.out.println("toBiQuinary");
        long result;

        result = BcdRegister.toBiQuinary(0x00L);
        assertEquals(0x00L, result);
        result = BcdRegister.toBiQuinary(0x01L);
        assertEquals(0x01L, result);
        result = BcdRegister.toBiQuinary(0x02L);
        assertEquals(0x02L, result);
        result = BcdRegister.toBiQuinary(0x03L);
        assertEquals(0x03L, result);
        result = BcdRegister.toBiQuinary(0x04L);
        assertEquals(0x04L, result);
        result = BcdRegister.toBiQuinary(0x05L);
        assertEquals(0x08L, result);
        result = BcdRegister.toBiQuinary(0x06L);
        assertEquals(0x09L, result);
        result = BcdRegister.toBiQuinary(0x07L);
        assertEquals(0x0aL, result);
        result = BcdRegister.toBiQuinary(0x08L);
        assertEquals(0x0bL, result);
        result = BcdRegister.toBiQuinary(0x09L);
        assertEquals(0x0cL, result);

        result = BcdRegister.toBiQuinary(0x9999L);
        assertEquals(0xccccL, result);

        result = BcdRegister.toBiQuinary(0x1234567890L);
        assertEquals(0x123489abc0L, result);

        result = BcdRegister.toBiQuinary(0x40L);
        assertEquals(0x40L, result);
        result = BcdRegister.toBiQuinary(0x50L);
        assertEquals(0x80L, result);

        result = BcdRegister.toBiQuinary(0x400L);
        assertEquals(0x400L, result);
        result = BcdRegister.toBiQuinary(0x500L);
        assertEquals(0x800L, result);

        result = BcdRegister.toBiQuinary(0x4000L);
        assertEquals(0x4000L, result);
        result = BcdRegister.toBiQuinary(0x5000L);
        assertEquals(0x8000L, result);

        result = BcdRegister.toBiQuinary(0x4444444444444444L);
        assertEquals(0x4444444444444444L, result);
        result = BcdRegister.toBiQuinary(0x5555555555555555L);
        assertEquals(0x8888888888888888L, result);

        result = BcdRegister.toBiQuinary(0x4000000000000000L);
        assertEquals(0x4000000000000000L, result);

        result = BcdRegister.toBiQuinary(0x5000000000000000L);
        assertEquals(0x8000000000000000L, result);

        result = BcdRegister.toBiQuinary(0x6000000000000000L);
        assertEquals(0x9000000000000000L, result);

        result = BcdRegister.toBiQuinary(0x7000000000000000L);
        assertEquals(0xa000000000000000L, result);

        result = BcdRegister.toBiQuinary(0x8000000000000000L);
        assertEquals(0xb000000000000000L, result);

        result = BcdRegister.toBiQuinary(0x9000000000000000L);
        assertEquals(0xc000000000000000L, result);

        return;
    }

    /**
     * Test of toString method, of class BcdRegister.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        BcdRegister bs;

        bs = new BcdRegister(1);

        assertEquals("0000[0]", bs.toString());

        bs = new BcdRegister(2);

        assertEquals("0000[0]", bs.toString());

        bs = new BcdRegister(32);

        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);

        assertEquals("0001[1] 0000[0] 0010[2]", bs.toString());

        bs = new BcdRegister(3);

        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);

        assertEquals("0010[2] 0100[4] 0011[3]", bs.toString());

        bs = new BcdRegister(10);

        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);

        assertEquals("1001[9] 1000[8] 0111[7] 0110[6] 0101[5] "
                +"0100[4] 0011[3] 0010[2] 0001[1] 0000[0]", bs.toString());

        return;
    }

    /**
     * Test of pushLsb method, of class BcdRegister.
     */
    @Test
    public void testPushLsb_boolean() {
        System.out.println("pushLsb");

        BcdRegister bs;

        bs = new BcdRegister(3);

        bs.pushLsb(true);
        bs.pushLsb(true);
        bs.pushLsb(true);
        bs.pushLsb(true);
        bs.pushLsb(false);
        bs.pushLsb(false);
        bs.pushLsb(true);
        bs.pushLsb(true);

        assertEquals(3, bs.getDigit(0));
        assertEquals(4, bs.getDigit(1));
        assertEquals(2, bs.getDigit(2));

        return;
    }

    /**
     * Test of pushLsb method, of class BcdRegister.
     */
    @Test
    public void testPushLsb_int() {
        System.out.println("pushLsb");

        BcdRegister bs;

        bs = new BcdRegister(3);

        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);

        assertEquals(3, bs.getDigit(0));
        assertEquals(4, bs.getDigit(1));
        assertEquals(2, bs.getDigit(2));

        bs = new BcdRegister(10);

        for(int ct = 1; ct <= 31; ct++){
            bs.pushLsb(1);
        }

        assertEquals(7, bs.getDigit(0));
        assertEquals(4, bs.getDigit(1));
        assertEquals(6, bs.getDigit(2));
        assertEquals(3, bs.getDigit(3));
        assertEquals(8, bs.getDigit(4));
        assertEquals(4, bs.getDigit(5));
        assertEquals(7, bs.getDigit(6));
        assertEquals(4, bs.getDigit(7));
        assertEquals(1, bs.getDigit(8));
        assertEquals(2, bs.getDigit(9));
        // 2147483647

        return;
    }

    /**
     * Test of getMaxDigits method, of class BcdRegister.
     */
    @Test
    public void testGetMaxDigits() {
        System.out.println("getMaxDigits");

        BcdRegister bs;

        bs = new BcdRegister();
        assertEquals(16, bs.getMaxDigits());

        bs = new BcdRegister(1);
        assertEquals(8, bs.getMaxDigits());

        bs = new BcdRegister(7);
        assertEquals(8, bs.getMaxDigits());

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        bs = new BcdRegister(9);
        assertEquals(16, bs.getMaxDigits());

        bs = new BcdRegister(16);
        assertEquals(16, bs.getMaxDigits());

        bs = new BcdRegister(17);
        assertEquals(24, bs.getMaxDigits());

        return;
    }

    /**
     * Test of getPrecision method, of class BcdRegister.
     */
    @Test
    public void testGetPrecision() {
        System.out.println("getPrecision");

        BcdRegister bs;

        bs = new BcdRegister();
        assertEquals(1, bs.getPrecision());

        bs.clear();
        assertEquals(1, bs.getPrecision());

        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        // 9
        assertEquals(1, bs.getPrecision());

        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        // 10
        assertEquals(2, bs.getPrecision());

        bs.clear();
        assertEquals(1, bs.getPrecision());

        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        // 99
        assertEquals(2, bs.getPrecision());

        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        // 100
        assertEquals(3, bs.getPrecision());

        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        // 7
        assertEquals(1, bs.getPrecision());
        bs.pushLsb(1);
        // 15
        assertEquals(2, bs.getPrecision());

        return;
    }

    /**
     * Test of toIntArray method, of class BcdRegister.
     */
    @Test
    public void testToIntArray() {
        System.out.println("toIntArray");

        BcdRegister bs;
        int[] result;
        int precision;

        bs = new BcdRegister(3);

        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);

        result = new int[5];
        precision = bs.toIntArray(result, 1);

        assertEquals(3, precision);
        assertArrayEquals(new int[]{0,2,4,3,0}, result);

        bs = new BcdRegister(10);

        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);

        result = new int[10];
        precision = bs.toIntArray(result, 0);

        assertEquals(10, precision);
        assertArrayEquals(new int[]{9,8,7,6,5,4,3,2,1,0}, result);

        return;
    }

    @Test
    public void testMersennePrime(){
        System.out.println("MersennePrime");

        BcdRegister bs;

        bs = new BcdRegister(8);
        assertEquals(8, bs.getMaxDigits());

        TestValue.pushMersenne44(bs);
        assertEquals(8, bs.getPrecision());

        assertEquals(1, bs.getDigit(0));
        assertEquals(7, bs.getDigit(1));
        assertEquals(8, bs.getDigit(2));
        assertEquals(7, bs.getDigit(3));
        assertEquals(6, bs.getDigit(4));
        assertEquals(9, bs.getDigit(5));
        assertEquals(3, bs.getDigit(6));
        assertEquals(5, bs.getDigit(7));

        return;
    }

    /**
     * Test of clzNibble method, of class BcdRegister.
     */
    @Test
    public void testClzNibble_int() {
        System.out.println("clzNibble");

        assertEquals(8, BcdRegister.clzNibble(0x00000000));
        assertEquals(7, BcdRegister.clzNibble(0x0000000f));
        assertEquals(6, BcdRegister.clzNibble(0x000000ff));
        assertEquals(5, BcdRegister.clzNibble(0x00000fff));
        assertEquals(4, BcdRegister.clzNibble(0x0000ffff));
        assertEquals(3, BcdRegister.clzNibble(0x000fffff));
        assertEquals(2, BcdRegister.clzNibble(0x00ffffff));
        assertEquals(1, BcdRegister.clzNibble(0x0fffffff));
        assertEquals(0, BcdRegister.clzNibble(0xffffffff));

        assertEquals(7, BcdRegister.clzNibble(0x00000001));
        assertEquals(6, BcdRegister.clzNibble(0x00000010));
        assertEquals(1, BcdRegister.clzNibble(0x01000000));
        assertEquals(0, BcdRegister.clzNibble(0x10000000));

        assertEquals(6, BcdRegister.clzNibble(0x0000001f));
        assertEquals(0, BcdRegister.clzNibble(0x1fffffff));

        return;
    }

    /**
     * Test of clzNibble method, of class BcdRegister.
     */
    @Test
    public void testClzNibble_long() {
        System.out.println("clzNibble");

        assertEquals(16, BcdRegister.clzNibble(0x0000000000000000L));
        assertEquals(15, BcdRegister.clzNibble(0x000000000000000fL));
        assertEquals(14, BcdRegister.clzNibble(0x00000000000000ffL));
        assertEquals(13, BcdRegister.clzNibble(0x0000000000000fffL));
        assertEquals(12, BcdRegister.clzNibble(0x000000000000ffffL));
        assertEquals(11, BcdRegister.clzNibble(0x00000000000fffffL));
        assertEquals(10, BcdRegister.clzNibble(0x0000000000ffffffL));
        assertEquals( 9, BcdRegister.clzNibble(0x000000000fffffffL));
        assertEquals( 8, BcdRegister.clzNibble(0x00000000ffffffffL));
        assertEquals( 7, BcdRegister.clzNibble(0x0000000fffffffffL));
        assertEquals( 6, BcdRegister.clzNibble(0x000000ffffffffffL));
        assertEquals( 5, BcdRegister.clzNibble(0x00000fffffffffffL));
        assertEquals( 4, BcdRegister.clzNibble(0x0000ffffffffffffL));
        assertEquals( 3, BcdRegister.clzNibble(0x000fffffffffffffL));
        assertEquals( 2, BcdRegister.clzNibble(0x00ffffffffffffffL));
        assertEquals( 1, BcdRegister.clzNibble(0x0fffffffffffffffL));
        assertEquals( 0, BcdRegister.clzNibble(0xffffffffffffffffL));

        assertEquals(15, BcdRegister.clzNibble(0x0000000000000001L));
        assertEquals(14, BcdRegister.clzNibble(0x0000000000000010L));
        assertEquals( 1, BcdRegister.clzNibble(0x0100000000000000L));
        assertEquals( 0, BcdRegister.clzNibble(0x1000000000000000L));

        assertEquals(14, BcdRegister.clzNibble(0x000000000000001fL));
        assertEquals( 0, BcdRegister.clzNibble(0x1fffffffffffffffL));

        return;
    }

}
