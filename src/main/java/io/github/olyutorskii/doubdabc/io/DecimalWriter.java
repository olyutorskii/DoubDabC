/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc.io;

import io.github.olyutorskii.doubdabc.BcdRegister;
import io.github.olyutorskii.doubdabc.BitFeeder;
import io.github.olyutorskii.doubdabc.DecimalOut;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Decimal-form-integer writer with Double-Dabble algorithm.
 *
 * <p>It's similar to {@link java.io.PrintWriter}
 *
 * @see java.io.PrintWriter
 */
public class DecimalWriter extends FilterWriter{

    private final BcdRegister bcd;
    private final DecimalOut decOut;


    /**
     * Constructor.
     * @param out char output stream
     */
    public DecimalWriter(Writer out){
        super(out);

        this.bcd = new BcdRegister(BcdRegister.MAX_COL_UINT64);
        this.decOut = new DecimalOut(this.bcd);

        return;
    }


    /**
     * Print signed int value.
     *
     * <p>It's similar to {@link java.io.PrintWriter#print(int)}.
     *
     * @param iVal int value
     * @throws IOException output error
     * @see java.io.PrintWriter#print(int)
     */
    public void print(int iVal) throws IOException{
        boolean negSign;
        int absVal;

        if(iVal < 0){
            negSign = true;
            absVal = -iVal;
        }else{
            negSign = false;
            absVal =  iVal;
        }

        synchronized(this.bcd){
            BitFeeder.feedUInt32(this.bcd, absVal);

            synchronized(this.lock){
                if(negSign) append('-');
                this.decOut.writeDigits(this);
            }
        }

        return;
    }

    /**
     * Print signed long value.
     *
     * <p>It's similar to {@link java.io.PrintWriter#print(long)}.
     *
     * @param lVal long value
     * @throws IOException output error
     * @see java.io.PrintWriter#print(long)
     */
    public void print(long lVal) throws IOException{
        boolean negSign;
        long absVal;

        if(lVal < 0){
            negSign = true;
            absVal = -lVal;
        }else{
            negSign = false;
            absVal =  lVal;
        }

        synchronized(this.bcd){
            BitFeeder.feedUInt64(this.bcd, absVal);

            synchronized(this.lock){
                if(negSign) append('-');
                this.decOut.writeDigits(this);
            }
        }

        return;
    }

}
