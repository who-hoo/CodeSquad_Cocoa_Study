# 예외 Study

[Lesson: Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html)

try-catch 및 자동 자원 반환. try catch 원리 및 내부 처리.
try-catch는 printStackTrace, getMessage 정의 및 어떻게 구현하는지도 찾기

## 예외란?

프로그램 실행 중에 프로그램의 흐름을 방해하는 일(event)

예외 → 런타임 예외 & Exception

런타임 예외 : 예외 컨트롤 필요 없음. / unchecked exception

Exception : 예외 컨트롤 필수 / checked exception

예외가 발생하면 에러가 발생한 메소드에서 에러 객체를 생성(new Exception())하여 런타임 시스템에 전달(Thorw)하게 됨.

런타임 시스템은 에러 객체를 처리할 수 있는 메소드를 Call Stack 메모리에서 찾음

적절한 예외 처리기를 찾으면 런타임 시스템은 예외를 통과함 → 예외로 인한 비정상 종료를 막음

예외 처리기를 찾지 못하면 런타임 시스템은 종료됨.

![https://docs.oracle.com/javase/tutorial/figures/essential/exceptions-errorOccurs.gif](https://docs.oracle.com/javase/tutorial/figures/essential/exceptions-errorOccurs.gif)

## try-catch

예외를 처리할 때 사용하는 구문

try block, catch block, finally block 등으로 구성됨

```java
try {
	// 오류를 발생할 수 있는 코드 (checked exception)
	// 예외 발생 시 발생된 예외 객체를 만듬
} catch (Exception1 e1) {
	// try block에서 생성된 예외 객체가 Exception1이면
	// e1이 예외 객체를 가르킴
	// Exception1 예외 객체가 발생 시 처리할 코드
} catch (Exception2 e2) {
	// try block에서 Exception2 가 발생 시 처리할 코드
} finally {
	// 에러가 발생해도 무조건 실행되어야 하는 코드 (try or catch 코드가 실행중일 때 JVM이 종료되면 실행안될수도)
	// 무조건 try block에서 exit 할 때 실행됨
	// try block -> finally block -> catch block 순으로 실행
}
```

### catch block

2개 이상의 예외를 동시에 처리하는 방법 (단 예외가 부모, 자손 관계이면 안됨 → 부모에서 예외를 다 잡기 때문에 굳이 자손을 쓸 이유가 없음)

이때 참조변수 ex는 final이 선언되어 있음

```java
catch (IOException | SQLException ex) {
	logger.log(ex);
	throw ex;
}
```

### try-with-resources

파일을 닫거나 자원을 복구하는 코드를 finally block에 넣어서 resouce leak를 방지할 수 있음.

`Closeable` 을 가지고 있는 클래스들에 적용 가능

```java
try(){

} catch (Exception e) {

}
```

- 언제 close() 를 호출할까? → finally block이 호출될 때라고 생각하면 됨.
    - try블럭에서 예외가 발생했을 때 바로
    - close() 호출 이후 catch 블럭으로 넘어감.

Example

```java
import java.io.Closeable;
import java.io.IOException;

public class ExceptionTest {

    public static void main(String[] args) {
        try(TestClose testClose = new TestClose();){
            testClose.start();

            testClose.end();
        } catch (Exception e) {
            System.out.println("에러다!");
        }
        System.out.println("메인문 종료");
    }

}

class TestClose implements Closeable {

    public void start() {
        System.out.println("프로그램 시작");
    }

    @Override
    public void close() throws IOException {
        System.out.println("Close가 호출됨");
    }

    public void end() {
        System.out.println("프로그램 종료");
    }
}
```

```java
프로그램 시작
프로그램 종료
Close가 호출됨
메인문 종료
```

```java
import java.io.Closeable;
import java.io.IOException;

public class ExceptionTest {

    public static void main(String[] args) {
        try(TestClose testClose = new TestClose();){
            testClose.start();
            int[] a = new int[2];
            a[3] = 10;
            testClose.end();
        } catch (Exception e) {
            System.out.println("에러다!");
        }
        System.out.println("메인문 종료");
    }

}

class TestClose implements Closeable {

    public void start() {
        System.out.println("프로그램 시작");
    }

    @Override
    public void close() throws IOException {
        System.out.println("Close가 호출됨");
    }

    public void end() {
        System.out.println("프로그램 종료");
    }
}
```

```java
프로그램 시작
Close가 호출됨
에러다!
메인문 종료
```

```java
import java.io.Closeable;
import java.io.IOException;

public class ExceptionTest {

    public static void main(String[] args) throws Exception {
        try(TestClose testClose = new TestClose();){
            testClose.start();
            int[] a = new int[2];
            a[3] = 10;
            testClose.end();
        } catch (NullPointerException e) {
            System.out.println("에러다!");
        }
        System.out.println("메인문 종료");
    }

}

class TestClose implements Closeable {

    public void start() {
        System.out.println("프로그램 시작");
    }

    @Override
    public void close() throws IOException {
        System.out.println("Close가 호출됨");
    }

    public void end() {
        System.out.println("프로그램 종료");
    }
}
```

```java
프로그램 시작
Close가 호출됨
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 2
	at ExceptionTest.main(ExceptionTest.java:10)
```

## try-catch 에서 printStackTrace(), getMessage()

```java
Class Throwable {

void	printStackTrace()
// Prints this throwable and its backtrace to the standard error stream

String	getMessage()
// Returns the detail message string of this throwable.

}

// 모든 에러, 예외는 Throwable의 자식이다.
```

try 문에서 오류가 발생하면 예외 객체가 생성된다. 이후 catch문에서 예외 객체를 핸들링하게 되는데 이 때 printStackTrace() 또는 getMessage()를 사용하게 되면 예외 객체에 구현되어 있는 printStackTrace() 메서드와 getMessage() 메서드가 호출되게 된다.