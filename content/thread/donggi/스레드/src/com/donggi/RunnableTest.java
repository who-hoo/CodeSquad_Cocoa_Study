package com.donggi;

public class RunnableTest {

  public static void main(String[] args) {
    Runnable r = new runnableExam();
    Thread runnableThread = new Thread(r);

    runnableThread.start();
  }
}

class runnableExam implements Runnable {

  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println("Runnable 인터페이스를 사용한 스레드입니다 : " + Thread.currentThread().getName());
    }
  }
}
