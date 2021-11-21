//package com.example.thread;
//
//import java.util.ArrayList;
//
//public class ThreadWaitEx1 {
//    public static void main(String[] args) throws Exception {
//
//    }
//}
//
//class Customer implements Runnable {
//    private Cook.Table table;
//    private String food;
//
//    public Customer(Cook.Table table, String food) {
//        this.table = table;
//        this.food = food;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String name = Thread.currentThread().getName();
//            if (eatFood())
//                System.out.println(name + " ate a " + food);
//            else
//                System.out.println(name + " failed to eat. : ");
//        }
//    }
//
//    private boolean eatFood() {
//        return false;
//    }
//}
//
//
//class Cook implements Runnable {
//    private Table table;
//    private String food;
//
//    public Cook(Table table, String food) {
//        this.table = table;
//        this.food = food;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {Thread.sleep(10);} catch (InterruptedException e) {}
//            String name = Thread.currentThread().getName();
//            if (eatFood())
//                System.out.println(name + " ate a " + food);
//
//            else
//                System.out.println(name + " failed to eat. : ( ");
//        }
//    }
//
//    private boolean eatFood() {
//        return table.remove(food);
//    }
//}
//
//class Table {
//    String[] dishNames = {"donut", "donut", "burger"};
//    final int MAX_FOOD = 6;
//
//    private ArrayList<String> dishes = new ArrayList<>();
//
//    public void add(String dish) {
//        if (dishes.size() >= MAX_FOOD) return;
//        dishes.add(dish);
//        System.out.println("Dishes: " + dishes.toString());
//    }
//
//    public boolean remove(String dishName) {
//        for (int i = 0; i < dishes.size(); i++) {
//            if (dishName.equals(dishes.get(i))) {
//                dishes.remove(i);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public int dishNumber() {
//        return dishNames.length;
//    }
//}