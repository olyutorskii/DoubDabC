/*
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
public class DecimalTextTest {

    public DecimalTextTest() {
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
     * Test of length method, of class DecimalText.
     */
    @Test
    public void testLength() {
        System.out.println("length");

        BcdRegister bs;
        DecimalText chseq;

        bs = new BcdRegister(3);
        chseq = new DecimalText(bs);

        assertEquals(1, chseq.length());

        TestValue.push243(bs);

        assertEquals(3, chseq.length());

        return;
    }

    /**
     * Test of charAt method, of class DecimalText.
     */
    @Test
    public void testCharAt() {
        System.out.println("charAt");

        BcdRegister bs;
        DecimalText chseq;

        bs = new BcdRegister(3);
        chseq = new DecimalText(bs);

        assertEquals('0', chseq.charAt(0));

        TestValue.push243(bs);

        assertEquals('2', chseq.charAt(0));
        assertEquals('4', chseq.charAt(1));
        assertEquals('3', chseq.charAt(2));

        assertEquals(3, chseq.length());

        try{
            chseq.charAt(-1);
            fail();
        }catch(IndexOutOfBoundsException e){
            // GOOD
        }

        try{
            chseq.charAt(3);
            fail();
        }catch(IndexOutOfBoundsException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of subSequence method, of class DecimalText.
     */
    @Test
    public void testSubSequence() {
        System.out.println("subSequence");

        BcdRegister bs;
        DecimalText chseq;

        bs = new BcdRegister(3);
        chseq = new DecimalText(bs);

        assertEquals("0", chseq.subSequence(0, 1).toString());
        assertEquals("", chseq.subSequence(0, 0).toString());

        TestValue.push243(bs);

        assertEquals("", chseq.subSequence(0, 0).toString());
        assertEquals("2", chseq.subSequence(0, 1).toString());
        assertEquals("4", chseq.subSequence(1, 2).toString());
        assertEquals("3", chseq.subSequence(2, 3).toString());
        assertEquals("24", chseq.subSequence(0, 2).toString());
        assertEquals("43", chseq.subSequence(1, 3).toString());
        assertEquals("243", chseq.subSequence(0, 3).toString());
        assertEquals("", chseq.subSequence(2, 2).toString());

        try{
            chseq.subSequence(-1, 2);
            fail();
        }catch(IndexOutOfBoundsException e){
            // GOOD
        }

        try{
            chseq.subSequence(2, 1);
            fail();
        }catch(IndexOutOfBoundsException e){
            // GOOD
        }

        try{
            chseq.subSequence(1, 4);
            fail();
        }catch(IndexOutOfBoundsException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of toString method, of class DecimalText.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        BcdRegister bs;
        DecimalText chseq;
        String result;

        bs = new BcdRegister(10);
        chseq = new DecimalText(bs);

        result = chseq.toString();
        assertEquals("0", result);

        TestValue.push243(bs);

        result = chseq.toString();
        assertEquals("243", result);

        bs.clear();

        TestValue.push9876543210(bs);

        result = chseq.toString();
        assertEquals("9876543210", result);

        bs.clear();

        result = chseq.toString();
        assertEquals("0", result);

        return;
    }

}
