package com.example.thread.threadwait;

public class Main {
    public static void main(String[] args) throws Exception {
        Table table = new Table();
        new Thread(new Cook(table), "COOK_A").start();
        System.out.println("----------------------------------------");
        new Thread(new Customer(table, "donut"), "CUSTOMER_A").start();
        new Thread(new Customer(table, "burger"), "CUSTOMER_B").start();
        System.out.println("----------------------------------------");
        Thread.sleep(2000);
        System.exit(0);
    }
}
