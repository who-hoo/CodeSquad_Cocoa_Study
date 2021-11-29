package com.example.thread.threadwait;

public class Cook implements Runnable {
    private Table table;

    public Cook(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            int index = (int) (Math.random() * table.dishNumber());
            System.out.println(index);
            table.add(table.dishNames[index]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("예외 발생");
            }
            ;
        }
    }
}
