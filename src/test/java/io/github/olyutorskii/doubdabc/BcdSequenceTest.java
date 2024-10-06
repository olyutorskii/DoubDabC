/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.olyutorskii.doubdabc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class BcdSequenceTest {

    public BcdSequenceTest() {
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
     * Test of flushDigitTo method, of class BcdSequence.
     * @throws IOException io error
     */
    @Test
    public void testFlushDigitTo_Appendable() throws IOException {
        System.out.println("flushDigitTo");

        BcdRegister bs;
        BcdSequence ba;
        ByteArrayOutputStream os;
        Appendable app;

        bs = new BcdRegister(10);
        ba = new BcdSequence(bs);

        TestValue.push9876543210(bs);

        os = new ByteArrayOutputStream();
        app = new PrintStream(os, true, "UTF-8");
        ba.flushDigitTo(app);

        assertEquals(10, os.size());
        byte[] result = os.toByteArray();

        assertEquals((byte)'9', result[0]);
        assertEquals((byte)'8', result[1]);
        assertEquals((byte)'7', result[2]);
        assertEquals((byte)'6', result[3]);
        assertEquals((byte)'5', result[4]);
        assertEquals((byte)'4', result[5]);
        assertEquals((byte)'3', result[6]);
        assertEquals((byte)'2', result[7]);
        assertEquals((byte)'1', result[8]);
        assertEquals((byte)'0', result[9]);

        return;
    }

    /**
     * Test of flushDigitTo method, of class BcdSequence.
     */
    @Test
    public void testFlushDigitTo_CharBuffer() {
        System.out.println("flushDigitTo");

        BcdRegister bs;
        BcdSequence ba;
        CharBuffer cbuf;

        bs = new BcdRegister(10);
        ba = new BcdSequence(bs);

        TestValue.push9876543210(bs);

        cbuf = CharBuffer.allocate(10);
        ba.flushDigitTo(cbuf);

        cbuf.flip();

        assertEquals("9876543210", cbuf.toString());

        cbuf.clear();
        cbuf = cbuf.asReadOnlyBuffer();
        try{
            ba.flushDigitTo(cbuf);
            fail();
        }catch(ReadOnlyBufferException e){
            // GOOD
        }

        cbuf = CharBuffer.allocate(9);
        try{
            ba.flushDigitTo(cbuf);
            fail();
        }catch(BufferOverflowException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of flushDigitTo method, of class BcdSequence.
     * @throws IOException
     */
    @Test
    public void testFlushDigitTo_Writer() throws IOException {
        System.out.println("flushDigitTo");

        BcdRegister bs;
        BcdSequence ba;
        Writer writer;

        bs = new BcdRegister(10);
        ba = new BcdSequence(bs);

        TestValue.push9876543210(bs);

        writer = new StringWriter();
        ba.flushDigitTo(writer);

        assertEquals("9876543210", writer.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class BcdSequence.
     */
    @Test
    public void testFlushDigitTo_StringBuffer() {
        System.out.println("flushDigitTo");

        BcdRegister bs;
        BcdSequence ba;
        StringBuffer text;

        bs = new BcdRegister(10);
        ba = new BcdSequence(bs);

        TestValue.push9876543210(bs);

        text = new StringBuffer();
        ba.flushDigitTo(text);

        assertEquals("9876543210", text.toString());

        return;
    }

    /**
     * Test of flushDigitTo method, of class BcdSequence.
     */
    @Test
    public void testFlushDigitTo_StringBuilder() {
        System.out.println("flushDigitTo");

        BcdRegister bs;
        BcdSequence ba;
        StringBuilder text;

        bs = new BcdRegister(10);
        ba = new BcdSequence(bs);

        TestValue.push9876543210(bs);

        text = new StringBuilder();
        ba.flushDigitTo(text);

        assertEquals("9876543210", text.toString());

        return;
    }

    /**
     * Test of length method, of class BcdSequence.
     */
    @Test
    public void testLength() {
        System.out.println("length");

        BcdRegister bs;
        BcdSequence chseq;

        bs = new BcdRegister(3);
        chseq = new BcdSequence(bs);

        assertEquals(1, chseq.length());

        TestValue.push243(bs);

        assertEquals(3, chseq.length());

        return;
    }

    /**
     * Test of charAt method, of class BcdSequence.
     */
    @Test
    public void testCharAt() {
        System.out.println("charAt");

        BcdRegister bs;
        BcdSequence chseq;

        bs = new BcdRegister(3);
        chseq = new BcdSequence(bs);

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
     * Test of subSequence method, of class BcdSequence.
     */
    @Test
    public void testSubSequence() {
        System.out.println("subSequence");

        BcdRegister bs;
        BcdSequence chseq;

        bs = new BcdRegister(3);
        chseq = new BcdSequence(bs);

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
     * Test of toString method, of class BcdSequence.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        BcdRegister bs;
        BcdSequence chseq;
        String result;

        bs = new BcdRegister(10);
        chseq = new BcdSequence(bs);

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

        for(int ct = 0; ct < 29; ct++){
            bs.pushLsb(1);
        }

        result = chseq.toString();
        assertEquals("536870911", result);

        bs.clear();

        result = chseq.toString();
        assertEquals("0", result);

        return;
    }

}
