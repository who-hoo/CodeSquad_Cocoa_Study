package com.donggi;

public class RunTest extends Thread {
  String name;
  public RunTest(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    for (int i = 0; i < 3; i++) {
      System.out.println("안녕하세요. 스레드 -" + name + " 입니다.");
    }
  }

  public static void main(String[] args) {
    RunTest thread1 = new RunTest("thread1");
    RunTest thread2 = new RunTest("thread2");
    RunTest thread3 = new RunTest("thread3");
    RunTest thread4 = new RunTest("thread4");
    RunTest thread5 = new RunTest("thread5");

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();
  }
}
