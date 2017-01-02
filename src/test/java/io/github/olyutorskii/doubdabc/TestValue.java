/*
 * License : The MIT License
 * Copyright(c) 2017 olyutorskii
 */

package io.github.olyutorskii.doubdabc;

/**
 *
 */
class TestValue {

    private TestValue(){
    }

    static void push9876543210(BcdRegister bs) {
        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(0);
        return;
    }

    static void push243(BcdRegister bs) {
        bs.clear();
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(1);
        bs.pushLsb(0);
        bs.pushLsb(0);
        bs.pushLsb(1);
        bs.pushLsb(1);
        return;
    }

    private static final int POWER = 32_582_657;  // Mersenne prime #44

    static void pushMersenne44(BcdRegister bs){
        for(int ct = 0; ct < POWER; ct++){
            bs.pushLsb(1);
        }
        return;
    }

}
