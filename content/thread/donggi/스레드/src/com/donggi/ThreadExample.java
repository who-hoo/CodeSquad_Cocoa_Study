package com.donggi;

public class ThreadExample {

  public static void main(String[] args) {
    ThreadEx1_1 threadFirst = new ThreadEx1_1(); // Thread 를 상속받은 클래스의 인스턴스

    Runnable r = new ThreadEx1_2(); // Runnable 을 구현한 클래스의 인스턴스
    Thread threadSecond = new Thread(r); // 생성자 Thread

    threadFirst.start();
    threadSecond.start();
  }
}

class ThreadEx1_1 extends Thread {
  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println("첫번째 생성한 스레드는 : " + getName()); // 스레드를 직접 상속 받아왔기 때문에 getName() 을 바로 호출
    }
  }
}

class ThreadEx1_2 implements Runnable {

  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println("두번째로 생성한 스레드는 : " + Thread.currentThread().getName()); // ThreadEx1_2의 멤버는 run()만 있기 때문에 Thread 클래스의 getName()을 호출하기 위해선, Thread.currentThread().getName()으로 호출한다
    }
  }
}