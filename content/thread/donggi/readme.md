# 프로세스와 스레드
### 프로세스
- 프로세스란 실행 중인 프로그램이다. 프로그램을 실행하면 OS로부터 실행에 필요한 자원(메모리)을 할당받아 프로세스가 된다.
- 프로세스는 프로그램을 수행하는데 필요한 데이터와 메모리 등의 자원과 스레드로 구성되어있다.
- 
![process](https://user-images.githubusercontent.com/73376468/142754071-a2f9c6e7-61b6-4aa5-8e1a-199d2a4b9964.png)

### 스레드
- 스레드는 프로세스의 자원을 이용해 실제 작업을 수행한다.
- 모든 프로세스에는 최소한 하나 이상의 스레드가 존재한다.
- 하나의 프로세스가 가질 수 있는 스레드의 개수는 제한되어있지 않으나 프로세스의 메모리 한계에 따라 생성할 수 있는 스레드의 수가 결정된다.
- 우리가 사용하는 main 또한 스레드이다. 프로그램 실행 시 제일 먼저 실행되는 스레드이며, 메인스레드라고도 한다.

![thread](https://user-images.githubusercontent.com/73376468/142754112-cb391055-8df3-4cbb-a194-eb6c0b3a83ac.png)


### 멀티태스킹
- 여러 개의 프로세스가 동시에 실행되는 것

## 멀티스레드
- 하나의 프로세스 내에서 여러 스레드가 동시에 작업을 수행하는 것
- CPU의 코어가 한 번에 하나의 작업을 수행한다. 보통 처리해야하는 스레드가 코어의 개수보다 훨씬 많다. 코어는 아주 짧은 시간동안 여러 작업을 번갈아가며 수행함으로써 여러 작업들이 모두 동시에 수행되는 것처럼 보인다.
- 프로세스의 성능은 스레드의 개수에 비례하지 않는다

### 멀티스레드의 장단점
장점
- 시스템 자원을 효율적으로 사용할 수 있다
- 사용자에 대한 응답성이 향상된다. (예로 들면 싱글 스레드일 땐, 한 번에 한 가지 작업밖에 하지 못해 파일을 다운 받는다면 다른 작업을 할 수 없다. 멀티스레드일 땐 여러작업을 동시에 수행할 수 있다)
- 스레드별로 코드를 작성하기 때문에 코드가 간결해진다

단점
- 동기화
- 교착상태 (프로세스 내부에서 스레드의 작업이 겹칠 수 있다. 그렇기 때문에 코드를 유의해서 짜야한다)



## 스레드 구현과 실행
- 스레드를 구현하는 방법 : Thread 클래스를 상속, Runnable 인터페이스를 구현

```java
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
```

### 스레드 이름 변경
```java
public class ThreadA extends Thread {
    public ThreadA() {
        setName("ThreadA"); // setName을 이용하여 스레드 이름 변경
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName() + "가 출력한 내용");
        }
    }
}
```
### 스레드의 실행 - start()
start() 호출 시 스레드가 실행된다
```java
ThreadA.start();
```
- start() 호출 시 바로 실행되는 것이 아니라, 실행 대기 상태로 있다가 자신의 차례가 되면 실행된다. 실행 대기중인 스레드가 없으면 바로 실행된다.
- 한 번 실행이 종료된 스레드는 다시 실행할 수 없다. 하나의 스레드에 start()는 한 번만 호출될 수 있다. 중복 호출 시 IllegalThreadStateException 에러 발생
- 다시 실행하고 싶다면 새로운 스레드 생성 후 start() 호출


