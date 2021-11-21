package com.example.thread.threadwait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    public String[] dishNames = {"donut", "donut", "burger"};
    final int MAX_FOOD = 6;

    private List<String> dishes = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition forCook = lock.newCondition();
    private Condition forCust = lock.newCondition();

    public void add(String dish) {
        lock.lock();
        try {
            while (dishes.size() >= MAX_FOOD) {
                dishes.forEach(x -> System.out.print(x + " "));
                System.out.println();
                System.out.println("============================================");
                String name = Thread.currentThread().getName();

                System.out.println(name + " is waiting.");
                try {
                    System.out.println();
                    System.out.println("0.5초간 Cook 정지");
                    forCook.await(); // cook 쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                    System.out.println();
                } catch (InterruptedException e) {
                }
                ;
            }
            dishes.add(dish);
            forCust.signal(); // 기다리고 있는 CUST를 깨움
            System.out.println("Dishes= " + dishes.toString());
        } finally {
            lock.unlock();
        }
    }

    public void remove(String dishName) {
        lock.lock();
        String name = Thread.currentThread().getName();
        try {
            while (dishes.isEmpty()) {
                System.out.println(name + " is waiting.");
                try {
                    forCust.wait(); // CUST 쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Remove");
                }
            }
            while (true) {
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        forCust.signal(); // notify( ); 자고있는 COOK 꺠움
                        return;
                    }
                }
                try {
                    System.out.println(name + " is waiting.");
                    forCust.await(); // CUST 쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int dishNumber() {
        return dishNames.length;
    }

}
