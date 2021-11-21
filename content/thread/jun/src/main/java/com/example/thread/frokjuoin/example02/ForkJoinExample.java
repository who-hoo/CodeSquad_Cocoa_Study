package com.example.thread.frokjuoin.example02;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinExample {
    /**
     * 스레드 풀 생성
     */
    static final ForkJoinPool pool = new ForkJoinPool();

    public static void main(String[] args) throws Exception {
        long from = 1L;
        long to = 1000_000L;

        SumTask task = new SumTask(from, to);

        Long start = System.currentTimeMillis();
        Long result = pool.invoke(task);

        /**
         *  Elapsed time(경과시간)
         * */
        System.out.println("Elapsed time= " + (System.currentTimeMillis() - start));
        System.out.printf("sum of %d~%d=%d%n", from, to, result);
        System.out.println();

        System.out.println("---------------------------------------------------------");

        result = 0L;
        start = System.currentTimeMillis();

        for (long i = from; i <= to; i++)
            result += i;

        long end = System.currentTimeMillis();
        System.out.println("Elapsed time= " + (end - start));
        System.out.printf("sum of %d~%d=%d%n", from, to, result);
    }

}


