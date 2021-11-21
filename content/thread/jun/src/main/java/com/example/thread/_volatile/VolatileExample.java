package com.example.thread._volatile;

public class VolatileExample {
    static volatile long sharedVal = 3;
    volatile long sharedVal2 = 5;


    public static void main(String[] args) throws Exception {
        System.out.println(sharedVal);

        VolatileExample ve = new VolatileExample();
        System.out.println(ve.sharedVal2);
    }
}
