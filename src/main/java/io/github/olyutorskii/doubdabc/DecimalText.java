/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

import java.nio.CharBuffer;

/**
 * CharSequence wrapping access to Decimal digits.
 */
public class DecimalText implements CharSequence{

    private static final char[] DECCH_TBL = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };


    private final BcdRegister decimal;
    private final int[] intBuf;
    private final CharBuffer textOut;


    /**
     * Constructor.
     *
     * @param decimal BCD register
     * @throws NullPointerException argument is null
     */
    public DecimalText(BcdRegister decimal) throws NullPointerException{
        super();

        this.decimal = decimal;

        int digits = this.decimal.getMaxDigits();
        this.intBuf = new int[digits];
        this.textOut = CharBuffer.allocate(digits);

        return;
    }


    /**
     * Return digits columns.
     *
     * <p>There is no sign character.
     *
     * @return digits columns
     */
    @Override
    public int length(){
        int result = this.decimal.getPrecision();
        return result;
    }

    /**
     * Return each digit character.
     *
     * @param index digit position starting 0
     * @return digit character ('0' to '9')
     * @throws IndexOutOfBoundsException
     * Argument is negative, or, not less than <code>length()</code>
     */
    @Override
    public char charAt(int index) throws IndexOutOfBoundsException{
        int precision = this.decimal.getPrecision();

        if(index < 0 || precision <= index)
            throw new IndexOutOfBoundsException();

        int digitPos = precision - index - 1;
        int digit = this.decimal.getDigit(digitPos);

        char result = DECCH_TBL[digit];
        return result;
    }

    /**
     * Return fixed-ranged subsequence.
     *
     * @param start start index
     * @param end end index
     * @return ranged sub-sequence
     * @throws IndexOutOfBoundsException
     *     <code>start</code> is negative,
     *     <code>end</code> is less than <code>start</code>,
     *     or <code>end</code> is greater than length()
     */
    @Override
    public CharSequence subSequence(int start, int end)
            throws IndexOutOfBoundsException{
        if(start < 0 || end < start) throw new IndexOutOfBoundsException();

        int precision = this.decimal.getPrecision();
        if(end > precision) throw new IndexOutOfBoundsException();

        String result = encodeText(start, end);
        return result;
    }

    /**
     * Return fixed string text.
     *
     * @return fixed string
     */
    @Override
    public String toString(){
        int precision = this.decimal.getPrecision();
        String result = encodeText(0, precision);
        return result;
    }

    /**
     * Encode int array to text string.
     *
     * @param start start position
     * @param end end position
     * @return text
     */
    private String encodeText(int start, int end){
        this.decimal.toIntArray(this.intBuf, 0);

        this.textOut.clear();
        for(int idx = start; idx < end; idx++){
            int digit = this.intBuf[idx];
            char decimalCh = DECCH_TBL[digit];
            this.textOut.put(decimalCh);
        }

        this.textOut.flip();
        String result = this.textOut.toString();

        return result;
    }

}
