/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

/**
 * CharSequence wrapping access to Decimal digits.
 */
public class BcdSequence implements CharSequence{

    private final BcdRegister decimal;
    private final int[] intBuf;
    private final char[] charBuf;


    /**
     * Constructor.
     *
     * @param decimal BCD register
     * @throws NullPointerException argument is null
     */
    public BcdSequence(BcdRegister decimal) throws NullPointerException{
        super();

        this.decimal = decimal;

        int digits = this.decimal.getMaxDigits();
        this.intBuf = new int[digits];
        this.charBuf = new char[digits];

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

        // map [0 - 9](int) to ['0' - '9'](char)
        char result = (char)( digit | 0b0011_0000 );

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

        copyChar(start, end);
        String result =new String(this.charBuf, start, end - start);

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
        buildChar();
        String result =new String(this.charBuf, 0, precision);
        return result;
    }

    /**
     * Build char array data.
     * @return digits length
     */
    private int buildChar(){
        int precision = this.decimal.getPrecision();
        int result = copyChar(0, precision);
        return result;
    }

    /**
     * Build ranged char array data.
     * @param start start
     * @param end end
     * @return digits length
     */
    private int copyChar(int start, int end){
        int length = end - start;
        this.decimal.toIntArray(this.intBuf, 0);

        for(int idx = start; idx < end; idx++){
            int digit = this.intBuf[idx];

            // map [0 - 9](int) to ['0' - '9'](char)
            char decimalCh = (char)( digit | 0b0011_0000 );

            this.charBuf[idx] = decimalCh;
        }

        return length;
    }

}
