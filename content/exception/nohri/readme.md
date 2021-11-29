# 커스텀 예외

## 커스텀 예외란?
자바에서 제공하는 표준 예외 클래스로 많은 예외 상황을 처리할 수 있지만,  
프로그래머가 직접 예외 클래스를 만들어 예외 상황을 처리할 수 있다.  
프로그래머가 직접 만든 예외 클래스를 `커스텀 예외`라고한다.

## 만드는 방법
예외 클래스를 만들 때 우선 생각해야 할 것은 Checked로 할 것인가? Unchecked로 할 것인가이다.  
기준을 나눠보자면, `꼭 예외처리를 해야하는가?` 이다.  
- Checked Exception이 발생할 가능성이 있는 메소드라면 try/catch로 감싸거나 throw를 던져야 함.  
- Unchecked Exception의 경우 예측하지 못했던 상황에서 발생하는 예외가 아니기 때문에 예외처리를 하지 않아도 됨.
- Checked Exception = 사용자가 발생시키는 예외
- Unchecked Exception = 프로그래머의 실수로 발생시키는 예외.

위의 상황을 고려해서 Checked로 할 것인가? Unchecked로 할것인가를 정해야한다.


### 예시

```java
public class ExampleException extends RuntimeException {
    public ExampleException() {
        super();
    }
    
    public ExampleException(String message) {
        super(message);
    }
}
```
일반적인 상황에서는 RuntimeException을 상속받는 경우가 많다.

## 그렇다면 왜 커스텀 예외를 사용할까?

### 1.이름만으로 정보 전달이 가능하다.
`ArithmeticException` 만으로는 어떤 계산에서 산술예외가 발생했는지 알기 어렵다.  
`DivideException` 이 발생했다면, 나누기 과정에서 예외가 발생했다는것을 비교적 유추하기 쉽다.

### 2. 상세한 예외 정보를 제공할 수 있다.
기존의 예외에선 `IllegalArgumentException`이나 `IndexOutOfBoundsException`을 생각해볼 수 있을 것이다.  
예외 메시지로는 "범위를 벗어났습니다." 정도면 적당하다.
```java
if(index >= list.size()) {
    throw new IndexOutOfBoundsException("범위를 벗어났습니다.");
}
```
- 전체범위가 얼마인지, 요청한 index가 몇인지 알수가 없다.  
- 메세지에 넣으면 안될까? 단 한곳에서만 발생하는 예외라면 메세지에 넣어도 좋다.  
- 하지만 같은 예외가 발생하는 곳이 여러곳이라면 관리하기 어렵다.

```java
public class IllegalIndexException extends IndexOutOfBoundsException{
    public static final String message = "범위를 벗어났습니다. ";

    public IllegalIndexException(List<?> target, int index) {
        super(message + "size: " + target.size() + " index: " + index);
    }
}
```
이렇게 예외클래스로 작성을 하면 해당 예외클래스만 수정하더라도 같은 예외를 발생시키는 곳에 적용이 가능하다.

## 커스텀 예외가 최고인가?

커스텀 예외의 사용에 반대하는 의견들도 많다.

1. 예외 메시지로도 충분히 의미를 전달할 수 있다.  
   1. 메시지만 예외사항에 맞게 재정의해준다면 충분히 그 의미를 파악할 수 있다.  
  
2. 표준 예외를 사용하면 가독성이 높아진다.  
   1. 우리는 이미 익숙하고, 쓰임에 대해 잘 알고있는 예외들이 많다.
   2. 낯선 예외를 만났을 땐, 그 커스텀 익셉션을 파악하는 작업이 따라온다. 이 또한 비용이 될 수 있다.
3. 일일히 예외 클래스를 만들다보면 지나치게 커스텀 예외가 많아질 수 있다.
   1. 예외 클래스들을 하나하나 만들다보면 지나치게 많아질 수 있다. 이 디렉토리와 클래스를 관리하는 것 역시 일이다.

Effective Java에서도 아래와 같은 이유로 표준 예외 사용을 권장하고 있다.

1. 배우기 쉽고 사용하기 편리한 API를 만들 수 있다.
2. 표준 예외를 사용한 API는 가독성이 높다.
3. 예외도 재사용하는 것이 좋다. 예외 클래스의 수가 적을수록 프로그램의 메모리 사용량이 줄고, 클래스를 적재 시간도 줄어든다.






