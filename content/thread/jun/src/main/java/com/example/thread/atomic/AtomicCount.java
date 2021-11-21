package com.example.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCount {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void increment() {
        count.incrementAndGet();
    }

    public void decrement() {
        count.decrementAndGet();
    }

    public final int getCount() {
        return count.get();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(count);
        count.incrementAndGet();
        System.out.println("증가된 수-> " + count);
        count.decrementAndGet();
        System.out.println("감소된 수-> " + count);
    }
}
