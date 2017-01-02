/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class DecimalOutTest {

    public DecimalOutTest() {
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
     * Test of getDigitsLength method, of class DecimalOut.
     */
    @Test
    public void testGetDigitsLength() {
        System.out.println("getDigitsLength");

        BcdRegister bs;
        DecimalOut ba;

        bs = new BcdRegister(15);
        ba = new DecimalOut(bs);
        assertEquals(1, ba.getDigitsLength());

        TestValue.push9876543210(bs);
        assertEquals(10, ba.getDigitsLength());

        return;
    }

    /**
     * Test of writeDigits method, of class DecimalOut.
     * @throws IOException io error
     */
    @Test
    public void testWriteDigits_Appendable() throws IOException {
        System.out.println("writeDigits");

        BcdRegister bs;
        DecimalOut ba;
        ByteArrayOutputStream os;
        Appendable app;

        bs = new BcdRegister(10);
        ba = new DecimalOut(bs);

        TestValue.push9876543210(bs);

        os = new ByteArrayOutputStream();
        app = new PrintStream(os, true, "UTF-8");
        ba.writeDigits(app);

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
     * Test of writeDigits method, of class DecimalOut.
     */
    @Test
    public void testWriteDigits_CharBuffer() {
        System.out.println("writeDigits");

        BcdRegister bs;
        DecimalOut ba;
        CharBuffer cbuf;

        bs = new BcdRegister(10);
        ba = new DecimalOut(bs);

        TestValue.push9876543210(bs);

        cbuf = CharBuffer.allocate(10);
        ba.writeDigits(cbuf);

        cbuf.flip();

        assertEquals("9876543210", cbuf.toString());

        cbuf.clear();
        cbuf = cbuf.asReadOnlyBuffer();
        try{
            ba.writeDigits(cbuf);
            fail();
        }catch(ReadOnlyBufferException e){
            // GOOD
        }

        cbuf = CharBuffer.allocate(9);
        try{
            ba.writeDigits(cbuf);
            fail();
        }catch(BufferOverflowException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of writeDigits method, of class DecimalOut.
     * @throws IOException
     */
    @Test
    public void testWriteDigits_Writer() throws IOException {
        System.out.println("writeDigits");

        BcdRegister bs;
        DecimalOut ba;
        Writer writer;

        bs = new BcdRegister(10);
        ba = new DecimalOut(bs);

        TestValue.push9876543210(bs);

        writer = new StringWriter();
        ba.writeDigits(writer);

        assertEquals("9876543210", writer.toString());

        return;
    }

    /**
     * Test of writeDigits method, of class DecimalOut.
     */
    @Test
    public void testWriteDigits_StringBuffer() {
        System.out.println("writeDigits");

        BcdRegister bs;
        DecimalOut ba;
        StringBuffer text;

        bs = new BcdRegister(10);
        ba = new DecimalOut(bs);

        TestValue.push9876543210(bs);

        text = new StringBuffer();
        ba.writeDigits(text);

        assertEquals("9876543210", text.toString());

        return;
    }

    /**
     * Test of writeDigits method, of class DecimalOut.
     */
    @Test
    public void testWriteDigits_StringBuilder() {
        System.out.println("writeDigits");

        BcdRegister bs;
        DecimalOut ba;
        StringBuilder text;

        bs = new BcdRegister(10);
        ba = new DecimalOut(bs);

        TestValue.push9876543210(bs);

        text = new StringBuilder();
        ba.writeDigits(text);

        assertEquals("9876543210", text.toString());

        return;
    }

}
