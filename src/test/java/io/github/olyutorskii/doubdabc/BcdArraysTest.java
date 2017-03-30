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
public class BcdArraysTest {

    public BcdArraysTest() {
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
     * Test of int2ArabicArray method, of class BcdArrays.
     */
    @Test
    public void testInt2ArabicArray() {
        System.out.println("int2ArabicArray");

        int result;
        char[] buf;

        buf = new char[100];

        result = BcdArrays.int2ArabicArray(1, buf, 0);
        assertEquals(result, 1);
        assertEquals("1", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(1234567890, buf, 0);
        assertEquals(result, 10);
        assertEquals("1234567890", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(99999999, buf, 0);
        assertEquals(result, 8);
        assertEquals("99999999", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(100000000, buf, 0);
        assertEquals(result, 9);
        assertEquals("100000000", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(0, buf, 0);
        assertEquals(result, 0);
        assertEquals("", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(-1, buf, 0);
        assertEquals(result, 10);
        assertEquals("4294967295", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(Integer.MAX_VALUE, buf, 0);
        assertEquals(result, 10);
        assertEquals("2147483647", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(Integer.MIN_VALUE, buf, 0);
        assertEquals(result, 10);
        assertEquals("2147483648", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(234, buf, 0);
        assertEquals(result, 3);
        assertEquals('2', buf[0]);
        assertEquals('3', buf[1]);
        assertEquals('4', buf[2]);

        buf[0] = 'A';
        buf[1] = 'B';
        buf[2] = 'C';
        result = BcdArrays.int2ArabicArray(234, buf, 2);
        assertEquals(result, 3);
        assertEquals("AB234", new String(buf, 0, 2+result));

        return;
    }

}
