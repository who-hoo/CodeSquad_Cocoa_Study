package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadWaitExample04 {

}

class Customer implements Runnable {
    private Table table;
    private String food;

    public Customer(Table table, String food) {
        this.table = table;
        this.food = food;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            String name = Thread.currentThread().getName();
            table.remove(food);
            System.out.println(name + " ate a " + food);
        }
    }
}


class Cook implements Runnable {
    private Table table;

    public Cook(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            int index = (int) (Math.random() * table.dishNumber());
            table.add(table.dishNames[index]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }

        }
    }
}

class Table {
    String[] dishNames = {"donut", "donut", "burger"};
    final int MAX_FOOD = 6;

    private List<String> dishes = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition forCook = lock.newCondition();
    private Condition forCust = lock.newCondition();


    public void remove(String dish) {
        while ((dishes.size() >= MAX_FOOD)) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting.");
        }

    }

    public double dishNumber() {
        return 0;
    }

    public void add(String dishName) {
    }
}
