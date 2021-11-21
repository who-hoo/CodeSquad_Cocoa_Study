# 프로세스와 스레드
### 프로세스
- 프로세스란 실행 중인 프로그램이다. 프로그램을 실행하면 OS로부터 실행에 필요한 자원(메모리)을 할당받아 프로세스가 된다.
- 프로세스는 프로그램을 수행하는데 필요한 데이터와 메모리 등의 자원과 스레드로 구성되어있다.

![process](https://user-images.githubusercontent.com/73376468/142754071-a2f9c6e7-61b6-4aa5-8e1a-199d2a4b9964.png)

### 스레드
- 스레드는 프로세스의 자원을 이용해 실제 작업을 수행한다.
- 모든 프로세스에는 최소한 하나 이상의 스레드가 존재한다.
- 하나의 프로세스가 가질 수 있는 스레드의 개수는 제한되어있지 않으나 프로세스의 메모리 한계에 따라 생성할 수 있는 스레드의 수가 결정된다.
- 우리가 사용하는 main 또한 스레드이다. 프로그램 실행 시 제일 먼저 실행되는 스레드이며, 메인스레드라고도 한다.

![thread](https://user-images.githubusercontent.com/73376468/142754112-cb391055-8df3-4cbb-a194-eb6c0b3a83ac.png)


### 멀티 프로세스
- 하나의 응용프로그램을 여러 개의 프로세스로 구성하여 각 프로세스가 하나의 작업(태스크)을 처리하도록 하는 것이다.

장점
- 여러개의 자식 프로세스 중 하나에 문제가 발생하면 해당 프로세스만 죽을뿐 문제가 확산되지 않는다.

단점
- Context Switching 에서의 오버헤드 (Context Switching은 CPU에서 여러 프로세스를 돌아가면서 작업을 처리하는 과정)
  - Context Switching 과정에서 캐쉬 메모리 초기화 등 무거운 작업이 진행되고 많은 시간이 소모되는 등의 오버헤드가 발생하게 된다.
  - 프로세스는 각각의 독립된 메모리 영역을 할당받았기 때문에 프로세스 사이에서 공유하는 메모리가 없어, Context Switching가 발생하면 캐쉬에 있는 모든 데이터를 모두 리셋하고 다시 캐쉬 정보를 불러와야 한다.
- 프로세스 사이의 어렵고 복잡한 통신 기법 (IPC)
  - 프로세스는 각각의 독립된 메모리 영역을 할당 받았기 때문에 하나의 프로그램에 속하는 프로세스들 사이의 변수를 공유할 수 없다

## 멀티스레드
- 하나의 프로세스 내에서 여러 스레드가 동시에 작업을 수행하는 것
- CPU의 코어가 한 번에 하나의 작업을 수행한다. 보통 처리해야하는 스레드가 코어의 개수보다 훨씬 많다. 코어는 아주 짧은 시간동안 여러 작업을 번갈아가며 수행함으로써 여러 작업들이 모두 동시에 수행되는 것처럼 보인다.
- 프로세스의 성능은 스레드의 개수에 비례하지 않는다

![multithread](https://user-images.githubusercontent.com/73376468/142754402-fb69cc53-bd0a-4a39-bdfc-8abc590ae43b.png)

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

Runnable 인터페이스는 Thread를 상속받은 클래스처럼 start() 메소드가 없다. 따라서 별도의 쓰레드를 생성 해주고 구현한 Runnable 인터페이스를 인자로 넘겨주어야 합니다.

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
### 스레드의 실행 - start() (start() 와 Run())
start() 호출 시 스레드가 실행된다
```java
ThreadA.start();
```
- start() 호출 시 바로 실행되는 것이 아니라, 실행 대기 상태로 있다가 자신의 차례가 되면 실행된다. 실행 대기중인 스레드가 없으면 바로 실행된다.
- 한 번 실행이 종료된 스레드는 다시 실행할 수 없다. 하나의 스레드에 start()는 한 번만 호출될 수 있다. 중복 호출 시 IllegalThreadStateException 에러 발생
- 다시 실행하고 싶다면 새로운 스레드 생성 후 start() 호출

### run()

- 스레드의 run() 메서드는 우리가 사용하는 main() 메서드와 같은 역할을 한다고 볼 수 있다. 스레드를 시작하면 run() 메서드 부터 시작하여 실행하기 때문이다.
- 스레드를 시작할 때에는 구현한 run() 메서드로 시작하는게 아닌, start()메서드로 시작해야 정상적으로 실행시킬 수 있다.
- 만약 스레드를 run() 메서드를 통해 실행시킨다면, 스레드는 main 메소드 위쪽에 스택으로 쌓이게 되며, 병행 처리를 하지 못하게 된다.

<img width="287" alt="스크린샷 2021-11-21 오후 5 31 40" src="https://user-images.githubusercontent.com/73376468/142755234-8beba426-8da3-4c9b-acbf-5c16e36dbed8.png">

<img width="276" alt="스크린샷 2021-11-21 오후 5 33 04" src="https://user-images.githubusercontent.com/73376468/142755275-0d66e127-d097-4939-a96b-397d8dbd575f.png">

- start() 메소드로 실행시키면 스레드가 순차적으로 실행되는게 아닌 병행 실행이 되기 때문에, 스레드의 실행이 일관적으로 나타나지 않는 것을 확인할 수 있다.
