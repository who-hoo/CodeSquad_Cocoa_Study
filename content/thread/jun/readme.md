# 1.lock 클래스

동기화를 하는 방법은 synchronized 블럭 외에도 java.util.concurrent.locks 패키지가 제공하는 lock 클래스들을 이용하는 방법이 있다. synchronized 블럭으로 동기화를 하면
자동적으로 lock이 잠기고 풀리기 때문에 편하지만 같은 메서드 내에서만 lock을 걸 수 있다는 제약이 불편하다.
<br/>

|번호|클래스|설명|
|:---:|:---:|:---|
|1|&nbsp;ReentrantLock|&nbsp;재진입이 가능한 Lock으로 가장 일반적인 배타 lock   |
|2|&nbsp;ReentrantReadWriteLock|&nbsp;읽기에는 공유적이고, 쓰기에는 배타적인 lock|
|3|&nbsp;StampedLock|&nbsp;ReentrantReadWriteLock에 낙관적 lock 기능을 추가&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|

<br/><br/><br/>

## 1-1. ReentrantLock

가장 일반적 lock. 특정 조건에서 lock을 풀고 나중에 다시 lock을 얻어 임계영역으로 들어와 추가 작업을 수행할 수 있다.
<br/><br/><br/>

## 1-2. ReentrantReadWriteLock

읽기/쓰기를 위한 lock을 제공한다. ReentrantLock 배타적 lock이라 무조건 lock이 있어야만 임계영역의 코드를 수행할 수 있지만, 이는 읽기 lock이 걸려있으면, 다른 쓰레드가 읽기 lock을
중복해 걸고 읽기를 수행할 수 있다. 읽기는 내용을 변경하지 않으므로 동시에 여러 쓰레드가 읽어도 문제가 되지 않는다. 그러나 읽기 lock이 걸린 상태에서 쓰기 lock은 허용되지 않는다. 반대 또한 마찬가지다.
<br/><br/><br/>

## 1-3. StampedLock

lock을 걸거나 해지할 때 long 타입의 정수값을 사용하며, 읽기와 쓰기를 위한 lock 외에 낙관적 읽기 lock이 추가된 것이다. 읽기 lock이 걸려있으면, 쓰기 lock을 얻기 위해서는 읽기 lock이
풀릴 때까지 기다려야하는데 비해 `낙관적 읽기 lock` 은 쓰기 lock에 의해 바로 풀린다. 그래서 낙관적 읽기에 실패하면 읽기 lock을 얻어서 다시 읽어 와야 한다. 무조건 읽기 lock을 걸지 않고 쓰기와
읽기가 충돌할 때만 쓰기가 끝난 후 읽기 lock을 거는 것이다.

````java
int getBalance(){
	long stamp = lock.tryOptimisticRead(); // 낙관적 읽기 lock을 건다.

	int curBalance = this.balance; // 공유 데이터인 balance를 읽어온다.
	
	if(!lock.validate(stamp)){ // 쓰기 lock에 의해 낙관적 읽기 lock이 풀렸는지 확인
		stamp = lock.readLock(); // lock이 풀렸으면, 읽기 lock을 얻으려고 기다린다.

		try{
			curBalance = this.balance; // 공유 데이터를 다시 읽어온다.
		}finally{
			lock.unlockRead(stamp); // 읽기 lock을 푼다.
		}
	}
	return curBalance; // 낙관적 읽기 lock이 풀리지 않았으면 곧바로 읽어온 값을 반환
}

````
<br/><br/><br/>


# 2. fork & join

- CPU 속도가 빠르게 발전했지만 이제는 한계치에 다다르기 시작했다.
- 멀티 코어를 잘 활용할 수 있는 멀티쓰레드 프로그래밍이 중요해지고 있다.

|번호|클래스|설명|
|:---:|:---:|:---|
|1|&nbsp;RecursiveAction|&nbsp;반환값이 없는 작업을 구현할 때 사용|
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

# 2. volatile/synchronize

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

<br/><br/><br/>

# Atmoic

자바에서는 병렬 프로그래밍을 위해 Concurrent 패키지와 같은 유용한 기능을 제공한다. 아래는 Atmoic으로 원자적 변순 선언과 이를 활용한 연산을 볼 수 있다.
<br/><br/>

````java
public class AtomicCount {
    private AtomicInteger count = new AtomicInteger(0);

    public synchronized void increment() {
        count.incrementAndGet();
    }

    public synchronized void decrement() {
        count.decrementAndGet();
    }

    public synchronized int getCount() {
        return count.get();
    }
}
````

<br/><br/><br/>

# 격리 수준(isolation level)

`여러 트랜잭션이 동시에 처리될 때` `특정 트랜잭션이 다른 트랜잭션에서 변경하거나 조회하는 데이터를 볼 수 있게 허용할지 말지를 결정`하는 것. 격리 수준은 크게 아래와 같이 네 가지로 나뉜다.
<br/><br/>

|No|Name |Dirty Read|Non-Repeatable Read|Phantom Read|
|:---:|:------------:|:---:|:---:|:---:|
|1|Read Uncommitted|O|O|O|
|2|Read Committed|X|O|O|
|3|Read Repeatable Read|X|X|O|
|4|Read Serializable|X|X|X|

<br/><br/><br/>

Read Uncommitted은 일반적인 데이터베이스에서는 거의 사용하지 않으며, 동시성이 중요한 데이터베이스에서는 Serializable 또한 잘 사용되지 않는다. 4개의 격리 수준에서 순서대로 뒤로 갈수록 각
트랜잭션 간의 데이터 격립 정도가 높아지며, (대체로) 동시 처리 성능도 떨어진다. *그러나 사실 Serializable 격리수준이 아니라면 격리 수준이 높아진다해서 크게 성능 개선이나 저하는 발샣아지 않는다.

<br/><br/><br/>

## DB 충돌 상황

데이터베이스에 접근해서 데이터를 수정할 때 동시에 수정이 일어나는 경우 충돌이 발생한다. 이때, 이를 해결할 수 있는 방법은 크게 아래와 같이 두 가지가 있다.

- 테이블 row에 접근 시 Lock을 걸어 다른 Lock이 없을 경우에만 수정을 가능하게 한다.
- 수정할 때 내가 먼저 이 값을 수정했다고 명시하여 다른 사람이 동일한 조건으로 값을 수정할 수 없게 한다.

<br/><br/><br/>

## 비관적 락(Pessimistic lock)

`자원에 대한 동시성이 발생하 것이라보고 자원에 접근 할 때 Lock을 걸어 해당 자원을 선점하는 형태`. 일반적으로 많이 사용하는 형태의 락이지만 교착 상태와 같은 문제를 발생시킬 수 있다. 이는 트랜잭션의 시작
시 Shared Lock 또는 Exclusive Lock을 걸고 시작한다. Shared Lock은 write를 하기 위해 EXclusive Lock을 획득해야 하는데, SHAred Lock이 다른 트랜잭션에 묶여
있으면 해당 Lock을 얻지 못해 업데이트를 할 수 없다. 이를 수정하기 위해서는 해당 트랜잭션을 제외하고 모든 트랜잭션이 종료되어야 한다. *즉, Commit이 완료되어야 한다.

<br/><br/><br/>

## 낙관적 락(Optimistic Lock)

`자원을 미리 선점하지 않고 변경할 때 Version을 확인하여 동시성을 제어하는 방식`. 업데이트 시 버전 정보를 넘겨서 해당 버전으로 데이터가 존재할 경우 업데이트 진행하고, 이미 다른 처리에 의하여 버전이
달라졌을 경우 에러를 발생시킨다. 에러가 발생했을 경우 Application level에서 후처리를 진행한다.

<br/><br/><br/>

## Rollback

데이터를 원래 상태로 되돌리는 것. 비관적 락은 데이터 베이스 단에서 전체 롤백이 발생하며, 모든 데이터가 이전 상태로 돌아간다. 반면 낙관적 락은 애플리케이션 단에서 롤백을 수동으로 처리해줘야 한다.
<br/>

|번호|종류|설명|
|:---:|:---:|:---|
|1|&nbsp;비관적 락|&nbsp;데이터 베이스 단에서 전체 Rollback이 발생|
|2|&nbsp;낙관적 락|&nbsp;Application 단에서 롤백을 수동으로 설정|

<br/><br/><br/>

## Conclusion

비관적 락은 트랜잭션을 필요로 하며, 충돌을 감지할 수 있다. 또한 충돌이 발생했을 때 데이터의 정합성에 대해 신경쓰지 않아도 된다. 반면 낙관적 락은 트랜잭션을 필요로 하지 않으며 기본적인 성능은 비관적 락보다 더
좋다. 하지만 충돌이 발생헀을 때 데이터의 정합성을 보장하기 위해 롤백처리를 애플리케이션 레벨에서 처리해줘야 하며, 이에 대한 추가비용이 발생한다.

<br/><br/><br/><br/><br/><br/><br/><br/><br/>