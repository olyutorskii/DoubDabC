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
public class BcdUtilsTest {

    public BcdUtilsTest() {
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
     * Test of toBiQuinary method, of class BcdRegister.
     */
    @Test
    public void testToBiQuinary_int() {
        System.out.println("toBiQuinary");

        int result;

        result = BcdUtils.toBiQuinary(0x00);
        assertEquals(0x00, result);
        result = BcdUtils.toBiQuinary(0x01);
        assertEquals(0x01, result);
        result = BcdUtils.toBiQuinary(0x02);
        assertEquals(0x02, result);
        result = BcdUtils.toBiQuinary(0x03);
        assertEquals(0x03, result);
        result = BcdUtils.toBiQuinary(0x04);
        assertEquals(0x04, result);
        result = BcdUtils.toBiQuinary(0x05);
        assertEquals(0x08, result);
        result = BcdUtils.toBiQuinary(0x06);
        assertEquals(0x09, result);
        result = BcdUtils.toBiQuinary(0x07);
        assertEquals(0x0a, result);
        result = BcdUtils.toBiQuinary(0x08);
        assertEquals(0x0b, result);
        result = BcdUtils.toBiQuinary(0x09);
        assertEquals(0x0c, result);

        result = BcdUtils.toBiQuinary(0x40);
        assertEquals(0x40, result);
        result = BcdUtils.toBiQuinary(0x50);
        assertEquals(0x80, result);

        result = BcdUtils.toBiQuinary(0x400);
        assertEquals(0x400, result);
        result = BcdUtils.toBiQuinary(0x500);
        assertEquals(0x800, result);

        result = BcdUtils.toBiQuinary(0x4000);
        assertEquals(0x4000, result);
        result = BcdUtils.toBiQuinary(0x5000);
        assertEquals(0x8000, result);

        result = BcdUtils.toBiQuinary(0x9999);
        assertEquals(0xcccc, result);

        result = BcdUtils.toBiQuinary(0x3456);
        assertEquals(0x3489, result);

        result = BcdUtils.toBiQuinary(0x40_00_00_00);
        assertEquals(0x40_00_00_00, result);

        result = BcdUtils.toBiQuinary(0x50_00_00_00);
        assertEquals(0x80_00_00_00, result);

        result = BcdUtils.toBiQuinary(0x60_00_00_00);
        assertEquals(0x90_00_00_00, result);

        result = BcdUtils.toBiQuinary(0x70_00_00_00);
        assertEquals(0xa0_00_00_00, result);

        result = BcdUtils.toBiQuinary(0x80_00_00_00);
        assertEquals(0xb0_00_00_00, result);

        result = BcdUtils.toBiQuinary(0x90_00_00_00);
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

        result = BcdUtils.toBiQuinary(0x00L);
        assertEquals(0x00L, result);
        result = BcdUtils.toBiQuinary(0x01L);
        assertEquals(0x01L, result);
        result = BcdUtils.toBiQuinary(0x02L);
        assertEquals(0x02L, result);
        result = BcdUtils.toBiQuinary(0x03L);
        assertEquals(0x03L, result);
        result = BcdUtils.toBiQuinary(0x04L);
        assertEquals(0x04L, result);
        result = BcdUtils.toBiQuinary(0x05L);
        assertEquals(0x08L, result);
        result = BcdUtils.toBiQuinary(0x06L);
        assertEquals(0x09L, result);
        result = BcdUtils.toBiQuinary(0x07L);
        assertEquals(0x0aL, result);
        result = BcdUtils.toBiQuinary(0x08L);
        assertEquals(0x0bL, result);
        result = BcdUtils.toBiQuinary(0x09L);
        assertEquals(0x0cL, result);

        result = BcdUtils.toBiQuinary(0x9999L);
        assertEquals(0xccccL, result);

        result = BcdUtils.toBiQuinary(0x1234567890L);
        assertEquals(0x123489abc0L, result);

        result = BcdUtils.toBiQuinary(0x40L);
        assertEquals(0x40L, result);
        result = BcdUtils.toBiQuinary(0x50L);
        assertEquals(0x80L, result);

        result = BcdUtils.toBiQuinary(0x400L);
        assertEquals(0x400L, result);
        result = BcdUtils.toBiQuinary(0x500L);
        assertEquals(0x800L, result);

        result = BcdUtils.toBiQuinary(0x4000L);
        assertEquals(0x4000L, result);
        result = BcdUtils.toBiQuinary(0x5000L);
        assertEquals(0x8000L, result);

        result = BcdUtils.toBiQuinary(0x4444444444444444L);
        assertEquals(0x4444444444444444L, result);
        result = BcdUtils.toBiQuinary(0x5555555555555555L);
        assertEquals(0x8888888888888888L, result);

        result = BcdUtils.toBiQuinary(0x4000000000000000L);
        assertEquals(0x4000000000000000L, result);

        result = BcdUtils.toBiQuinary(0x5000000000000000L);
        assertEquals(0x8000000000000000L, result);

        result = BcdUtils.toBiQuinary(0x6000000000000000L);
        assertEquals(0x9000000000000000L, result);

        result = BcdUtils.toBiQuinary(0x7000000000000000L);
        assertEquals(0xa000000000000000L, result);

        result = BcdUtils.toBiQuinary(0x8000000000000000L);
        assertEquals(0xb000000000000000L, result);

        result = BcdUtils.toBiQuinary(0x9000000000000000L);
        assertEquals(0xc000000000000000L, result);

        return;
    }

    /**
     * Test of clzNibble method, of class BcdRegister.
     */
    @Test
    public void testClzNibble_int() {
        System.out.println("clzNibble");

        assertEquals(8, BcdUtils.clzNibble(0x00000000));
        assertEquals(7, BcdUtils.clzNibble(0x0000000f));
        assertEquals(6, BcdUtils.clzNibble(0x000000ff));
        assertEquals(5, BcdUtils.clzNibble(0x00000fff));
        assertEquals(4, BcdUtils.clzNibble(0x0000ffff));
        assertEquals(3, BcdUtils.clzNibble(0x000fffff));
        assertEquals(2, BcdUtils.clzNibble(0x00ffffff));
        assertEquals(1, BcdUtils.clzNibble(0x0fffffff));
        assertEquals(0, BcdUtils.clzNibble(0xffffffff));

        assertEquals(7, BcdUtils.clzNibble(0x00000001));
        assertEquals(6, BcdUtils.clzNibble(0x00000010));
        assertEquals(1, BcdUtils.clzNibble(0x01000000));
        assertEquals(0, BcdUtils.clzNibble(0x10000000));

        assertEquals(6, BcdUtils.clzNibble(0x0000001f));
        assertEquals(0, BcdUtils.clzNibble(0x1fffffff));

        return;
    }

    /**
     * Test of clzNibble method, of class BcdRegister.
     */
    @Test
    public void testClzNibble_long() {
        System.out.println("clzNibble");

        assertEquals(16, BcdUtils.clzNibble(0x0000000000000000L));
        assertEquals(15, BcdUtils.clzNibble(0x000000000000000fL));
        assertEquals(14, BcdUtils.clzNibble(0x00000000000000ffL));
        assertEquals(13, BcdUtils.clzNibble(0x0000000000000fffL));
        assertEquals(12, BcdUtils.clzNibble(0x000000000000ffffL));
        assertEquals(11, BcdUtils.clzNibble(0x00000000000fffffL));
        assertEquals(10, BcdUtils.clzNibble(0x0000000000ffffffL));
        assertEquals( 9, BcdUtils.clzNibble(0x000000000fffffffL));
        assertEquals( 8, BcdUtils.clzNibble(0x00000000ffffffffL));
        assertEquals( 7, BcdUtils.clzNibble(0x0000000fffffffffL));
        assertEquals( 6, BcdUtils.clzNibble(0x000000ffffffffffL));
        assertEquals( 5, BcdUtils.clzNibble(0x00000fffffffffffL));
        assertEquals( 4, BcdUtils.clzNibble(0x0000ffffffffffffL));
        assertEquals( 3, BcdUtils.clzNibble(0x000fffffffffffffL));
        assertEquals( 2, BcdUtils.clzNibble(0x00ffffffffffffffL));
        assertEquals( 1, BcdUtils.clzNibble(0x0fffffffffffffffL));
        assertEquals( 0, BcdUtils.clzNibble(0xffffffffffffffffL));

        assertEquals(15, BcdUtils.clzNibble(0x0000000000000001L));
        assertEquals(14, BcdUtils.clzNibble(0x0000000000000010L));
        assertEquals( 1, BcdUtils.clzNibble(0x0100000000000000L));
        assertEquals( 0, BcdUtils.clzNibble(0x1000000000000000L));

        assertEquals(14, BcdUtils.clzNibble(0x000000000000001fL));
        assertEquals( 0, BcdUtils.clzNibble(0x1fffffffffffffffL));

        return;
    }

}
