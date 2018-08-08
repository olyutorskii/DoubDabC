/*
 */

package io.github.olyutorskii.doubdabc;

import java.math.BigInteger;

/**
 * Mersenne Prime #32 to decimal converting benchmark test.
 *
 * It takes about 120sec on (Core i5 2.6GHz + JDK1.8(64bit))
 * @see <a target="_blank"
 * href="https://en.wikipedia.org/wiki/Mersenne_prime">Mersenne prime</a>
 */
public class MPrimeBench {

    static final int PWR = 756839;   // Mersenne Prime #32 is 2^756839 -1
    static final int COLS = 227832;  // *log10(2) +1

    public static void main(String[] args){
        System.out.print("Mersenne Prime #32 (2^756839 -1) = ");
        dumpM32();
        return;
    }

    static void dumpM32(){
        BcdRegister br = new BcdRegister(COLS);
        BcdSequence bs = new BcdSequence(br);
        for(int ct = 0; ct < PWR; ct++){
            br.pushLsb(1);
        }
        System.out.println(bs);
        return;
    }

    //  fast but spawning garbages hell, maybe
    static void dumpM32_BigInt(){
        BigInteger twopow = BigInteger.ONE.shiftLeft(PWR);
        BigInteger mprime = twopow.subtract(BigInteger.ONE);
        System.out.println(mprime);
        return;
    }

}
