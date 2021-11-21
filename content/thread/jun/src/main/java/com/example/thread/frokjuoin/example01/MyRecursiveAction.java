package com.example.thread.frokjuoin.example01;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class MyRecursiveAction extends RecursiveAction {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(6);

        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(128);
        forkJoinPool.invoke(myRecursiveAction);

//      Just wait until all tasks done
        try {
            forkJoinPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long workLoad;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        String threadName = Thread.currentThread().getName();
        if (this.workLoad > 16) {
            System.out.println("[" + LocalTime.now() + "]  [" + threadName + "]  " + " Splitting workLoad count: " + this.workLoad);
            sleep(1000);
            System.out.println("1");
            List<MyRecursiveAction> subtasks = new ArrayList<>(createSubtasks());
            System.out.println("Size= " + subtasks.size());

            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }

        } else {
            System.out.println("[" + LocalTime.now() + "]  [" + threadName + "]  " + " Doing workLoad : " + this.workLoad);
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();

        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);
        System.out.println("--");
//        MyRecursiveAction subtask4 = new MyRecursiveAction(this.workLoad / 2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);
//        subtasks.add(subtask4);

        return subtasks;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
