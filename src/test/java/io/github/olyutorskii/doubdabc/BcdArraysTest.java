/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class BcdArraysTest {

    public BcdArraysTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
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

        result = BcdArrays.int2ArabicArray(67_108_863, buf, 0);
        assertEquals(result, 8);
        assertEquals("67108863", new String(buf, 0, result));

        result = BcdArrays.int2ArabicArray(67_108_864, buf, 0);
        assertEquals(result, 8);
        assertEquals("67108864", new String(buf, 0, result));

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
