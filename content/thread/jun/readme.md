# fork & join

- CPU 속도가 빠르게 발전했지만 이제는 한계치에 다다르기 시작했다.
- 따라서 멀티 코어를 잘 활용할 수 있는 멀티쓰레드 프로그래밍이 점점 더 중요해지고 있다.

|번호|클래스|설명|
|:---:|:---:|:---|
|1|&nbsp;RecursiveAction|&nbsp;반환값이 없는 작업을 구현할 때 사용   |
|2|&nbsp;RecursiveTask|&nbsp;반환값이 있는 작업을 구현할 때 사용|

<br/><br/>

두 클래스 모두 Compute( )라는 추상 메서드를 가지고 있는데, 상속을 통해 이 추상 메서드를 구현하기만 하면 된다.
<br/>

<br/><br/>

# volatile

Java 변수를 Main Memory에 저장하겠다라는 것을 명시하는 키워드. 멀티 코어 프로세서는 코어마다 별도의 캐시가 존재한다. 코어는 메모리에서 읽어온 값을 캐시에 저장하고 캐시에서 값을 읽어서 작업하는데,
같은 값을 읽어올 때는 먼저 캐시에 있는지 확인하고 없을 때만 메모리에서 읽어온다. 만약 메모리에 저장된 변수의 값이 변경되었는데 캐시에 저장된 값이 갱신되지 않아서 메모리에 저장된 값이 다른 경우 갱신되지 않은
값을 읽어올 수 있다. 변수에 Volatile을 붙이는 대신 synchronized 블럭을 사용해도 같은 효과를 얻을 수 있는데, 캐시와 메모리간의 동기화가 이루어지기 때문에 값의 불일치가 해소되기 때문이다.
<br/>

```java
public void stop(){
        stopped=true;
        }

```

<br/><br/><br/>

## 원자화

JVM은 데이터를 4byte(32bit) 단위로 처리하기 때문에 int와 int보다 작은 타입들은 한 번에 읽고 쓰는 것이 가능하다. 하나의 명령어로 읽고 쓰기가 가능하기 때문에, 즉 더 이상 나눌 수 없는 최소의
작업단위이기 때문에 작업의 중간에 다른 쓰레드가 끼어들 수 없다. 그러나 크기가 8byte인 long, double 타입의 변수는 하나의 명령어로 값을 읽거나 쓸 수 없기 때문에, 변수의 값을 읽는 과정에 다른
쓰레드가 끼어들 수 있다.

이를 방지하기 위해 변수를 읽고 쓰는 모든 문장을 synchronized블럭으로 감쌀 수도 있지만 더 간단히 변수를 선언할 때 volatile을 붙이면 된다. 이를 통해 해당 변수에 대한 읽기/쓰기가 원자화 되는데,
이를 통해 작업의 단위를 최소화 하는 것이다. synchronized 블럭도 일종의 원자화인데 이는 여러 문장을 원자화함으로써 쓰레드의 동기화를 구현한 것이라 보면 된다. 

단, volatile은 변수의 읽기나 쓰기를
원자화할 뿐 동기화를 하는 것은 아니기 때문에 주의해야 한다. 동기화는 volatile이 아닌 synchronized를 사용해야 한다.  
<br/>

```java
public class VolatileExample {
    static volatile long sharedVal = 3;
    volatile long sharedVal2 = 5;

    public static void main(String[] args) throws Exception {
        System.out.println(sharedVal);

        VolatileExample ve = new VolatileExample();
        System.out.println(ve.sharedVal2);
    }
}
```

<br/><br/><br/>

상수에는 Volatile을 붙일 수 없는데, 상수는 변하지 않는 값이므로 멀티쓰레드에서도 안전하기 때문이다.
<br/>
````java
// 사용할 수 없다. 
final volatile long impossible = 4;
````
