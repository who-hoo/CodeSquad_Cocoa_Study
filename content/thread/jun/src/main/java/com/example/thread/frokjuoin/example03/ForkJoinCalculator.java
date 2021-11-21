package com.example.thread.frokjuoin.example03;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int last;
    public static final long THRESHOLD = 10_000;

    public ForkJoinCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinCalculator(long[] numbers, int start, int last) {
        this.numbers = numbers;
        this.start = start;
        this.last = last;
    }

    @Override
    protected Long compute() {
        int length = last - start;

        if (length <= THRESHOLD)
            return computeSequantially();
        ForkJoinCalculator leftTask = new ForkJoinCalculator(numbers, start, start + length / 2);
        leftTask.fork();
        System.out.println("LeftTask= "+leftTask);
        ForkJoinCalculator rightTask = new ForkJoinCalculator(numbers, start + length / 2, last);
        Long rightResult = rightTask.compute();

        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    public static void main(String[] args) throws Exception {
        long[] array = {1,2,3,4,5,6,7,8,9,10};
        ForkJoinCalculator calculator = new ForkJoinCalculator(array, 0, 10);;
        System.out.println(calculator.compute());
    }

    private Long computeSequantially() {
        long sum = 0;
        for (int i = start; i < last; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
