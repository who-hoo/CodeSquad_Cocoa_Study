# fork & join

- CPU 속도가 빠르게 발전했지만 이제는 한계치에 다다르기 시작했다.
- 멀티 코어를 잘 활용할 수 있는 멀티쓰레드 프로그래밍이 중요해지고 있다.

|번호|클래스|설명|
|:---:|:---:|:---|
|1|&nbsp;RecursiveAction|&nbsp;반환값이 없는 작업을 구현할 때 사용   |
|2|&nbsp;RecursiveTask|&nbsp;반환값이 있는 작업을 구현할 때 사용|

<br/><br/>

두 클래스 모두 compute( )라는 추상 메서드를 가지고 있는데, 상속을 통해 이 추상 메서드를 구현하기만 하면 된다.
<br/><br/><br/>

fork( )는 작업을 쓰레드의 작업 큐에 넣는 메서드이고 작업 큐에 들어간 작업은 더 이상 나눌 수 없을 때까지 나뉜다. 즉, compute( )로 작업을 나누고 fork( )로 작업 큐에 넣는 작업이 계속해서
반복된다. 그리고 나눠진 작업은 각 쓰레드가 골고루 나눠서 처리하고 작업의 결과는 JOIN( ) 메서드를 호출해 얻는다.

비동기 메서드는 메서드를 호출만 할 뿐, 그 결과를 기다리지 않는다. 따라서 for( )를 호출하면 결과를 기다리지 않고 반환문으로 넘어간다. 반환문에서도 compute( )메서드가 재귀호출될 때, join( )
메서드는 호출되지 않는다. 그러다 작업을 더 이상 나눌 수 없게 되었을 때, compute( )메서드의 재귀호출이 끝나고 join( )의 결과를 기다렸다가 결과를 반환한다. 즉, 재귀호출된 compute()가 모두
종료될 때 최종 결과를 얻는다.
<br/><br/>

|번호|메서드|설명|
|:---:|:---:|:---|
|1|&nbsp;fork( )|&nbsp; 비동기 메서드로 해당 작업을 쓰레드 풀의 작업 큐에 넣는다.   |
|2|&nbsp;join( )|&nbsp; 동기 메서드로 해당 작업의 수행이 끝날 때까지 기다렸다가 수행이 끝나면 그 결과를 반환한다.|

<br/><br/>

## 속도

하지만 무조건 병렬 처리가 좋은 것은 아니다. 작업을 쪼개고 큐에 넣고 이를 다시 합치는 과정 또한 비용이 발생하기 때문이다. 아래 예제를 실행시키면 단순히 for문을 사용하는 것이 속도가 더 빠른 것을 알 수
있다.  
<br/><br/>

```java
public class SumTask extends RecursiveTask<Long> {
    long from, to;

    public SumTask(long from, long to) {
        this.from = from;
        this.to = to;
    }

    protected Long compute() {
        long size = to - from + 1;

        if (size <= 5)
            return sum();

        long half = (from + to) / 2;

        SumTask left = new SumTask(from, half);
        SumTask right = new SumTask(half + 1, to);

        left.fork();
        return right.compute() + left.join();
    }

    private Long sum() {
        long temp = 0L;
        for (long i = from; i <= to; i++) {
            temp += i;
        }
        return temp;
    }
}
```

````java
public class ForkJoinExample {
    /**
     * 스레드 풀 생성
     */
    static final ForkJoinPool pool = new ForkJoinPool();

    public static void main(String[] args) throws Exception {
        long from = 1L;
        long to = 1000_000L;

        SumTask task = new SumTask(from, to);

        Long start = System.currentTimeMillis();
        Long result = pool.invoke(task);

        /**
         *  Elapsed time(경과시간)
         * */
        System.out.println("Elapsed time= " + (System.currentTimeMillis() - start));
        System.out.printf("sum of %d~%d=%d%n", from, to, result);
        System.out.println();

        System.out.println("---------------------------------------------------------");

        result = 0L;
        start = System.currentTimeMillis();

        for (long i = from; i <= to; i++)
            result += i;

        long end = System.currentTimeMillis();
        System.out.println("Elapsed time= " + (end - start));
        System.out.printf("sum of %d~%d=%d%n", from, to, result);
    }

}
````

<br/>

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

단, volatile은 변수의 읽기나 쓰기를 원자화할 뿐 동기화를 하는 것은 아니기 때문에 주의해야 한다. 동기화는 volatile이 아닌 synchronized를 사용해야 한다.  
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
final volatile long impossible=4;
````
