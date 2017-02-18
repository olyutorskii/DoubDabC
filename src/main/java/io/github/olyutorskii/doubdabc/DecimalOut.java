/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import java.io.IOException;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

/**
 * Output decimal number characters.
 *
 * <p>Negative value is not supported.
 */
public class DecimalOut {

    private final BcdRegister decimal;

    private final int[] intBuf;
    private final char[] charBuf;


    /**
     * Constructor.
     *
     * @param decimal BCD register
     * @throws NullPointerException argument is null
     */
    public DecimalOut(BcdRegister decimal) throws NullPointerException{
        super();

        this.decimal = decimal;

        int digits = this.decimal.getMaxDigits();
        this.intBuf = new int[digits];
        this.charBuf = new char[digits];

        return;
    }


    /**
     * Return digits length.
     *
     * <p>Letters of length will be written to the output.
     *
     * @return digits length
     */
    public int getDigitsLength(){
        int result = this.decimal.getPrecision();
        return result;
    }

    /**
     * Build char array data.
     * @return digits length
     */
    private int buildChar(){
        int precision = this.decimal.getPrecision();
        this.decimal.toIntArray(this.intBuf, 0);

        for(int idx = 0; idx < precision; idx++){
            int digit = this.intBuf[idx];

            // map [0 - 9](int) to ['0' - '9'](char)
            char decimalCh = (char)( digit | 0b0011_0000 );

            this.charBuf[idx] = decimalCh;
        }

        return precision;
    }

    /**
     * Write digits to Appendable.
     *
     * @param app output
     * @return digits length
     * @throws IOException If an I/O error occurs
     */
    public int writeDigits(Appendable app) throws IOException{
        int precision = buildChar();

        for(int idx = 0; idx < precision; idx++){
            char decimalCh = this.charBuf[idx];
            app.append(decimalCh);
        }

        return precision;
    }

    /**
     * Write digits to CharBuffer.
     *
     * @param cBuf output
     * @return digits length
     * @throws BufferOverflowException
     * If buffer's current position is not smaller than its limit
     * @throws ReadOnlyBufferException
     * If buffer is read-only
     */
    public int writeDigits(CharBuffer cBuf)
            throws BufferOverflowException, ReadOnlyBufferException{
        int precision = buildChar();
        cBuf.put(this.charBuf, 0, precision);
        return precision;
    }

    /**
     * Write digits to Writer.
     *
     * @param writer output
     * @return digits length
     * @throws IOException If an I/O error occurs
     */
    public int writeDigits(Writer writer) throws IOException{
        int precision = buildChar();
        writer.write(this.charBuf, 0, precision);
        return precision;
    }

    /**
     * Write digits to StringBuffer.
     *
     * @param buf output
     * @return digits length
     */
    public int writeDigits(StringBuffer buf){
        int precision = buildChar();
        buf.append(this.charBuf, 0, precision);
        return precision;
    }

    /**
     * Write digits to StringBuilder.
     *
     * @param buf output
     * @return digits length
     */
    public int writeDigits(StringBuilder buf){
        int precision = buildChar();
        buf.append(this.charBuf, 0, precision);
        return precision;
    }

}
