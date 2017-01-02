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
        return;
    }

    /**
     * Test of toBiQuinary method, of class BcdRegister.
     */
    @Test
    public void testToBiQuinary() {
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
    public void testGetDigitsHolder() {
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

}
