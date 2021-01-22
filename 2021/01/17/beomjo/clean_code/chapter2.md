# Chapter 2 - 의미 있는 이름

## 의도를 분명히 밝혀라
```
// Bad
int d; // 경과시간(단위: 날짜) 

// Good
int elapsedtimeInDays;
int daysSinceCreation;
int daysSinceModification;
int fileAgeInDays;
```
d라는 변수명은 어떤 의미도 들어나지않는다
경과 시간이나 날짜, 측적하려는 값과 단위를 표현하는 이름으로 네이밍해야한다
```
// Bad
public List<int[]> getThem() {
    List<int[]> list1= new ArrayList<int[]>();
    for(int[] x : theList)
      if (x[0]==4)
        list1.add(x);
    return list1;
}

// Good
public List<int[]> getFlaggedCells() {
    List<int[]> flaggedCells = new ArrayList<int[]>();
    for (int[] cell : gameBoard)
      if (celll[STATUS_VALUE]) 
        flaggedCells.add(cell);
    return flaggedCells;

}
```
Bad case에는 코드가 함축되어있어 코드맥락이 명시적으로 드러나지않는다
 함수명, 변수명, 상수명등 각 개념에 네이밍만 명확하게 수정하여도 코드 내용을 유추하기 쉬워진다.


## 그릇된 정보를 피하라
- 널리 쓰이는 의미가 있는 단어를 다른 의미로 사용하면 안된다
   - ex) hp, aiz, sco
- 여러 계정을 그룹을 묶을 때, 실제 `List`가 아니라면 `accountList`라 명명하면 안된다
    - 자료형이 List가 아닐경우 오해할 수 있는 소지가 있다
    - `accountGroup`,`branchOfAccounts`, `Accounts`라 명명하도록 한다
- 서로 흡사한 이름을 사용하지 않도록 주의한다
    - 서로 다른 모듈에서 각각  `XYZControllerForEfficientHandlingOfStrings`, `XYZControllerForEfficientStorageOfStrings`와 같이 사용한다면 비슷한 이름으로 혼란이 올 수 있다


## 의미 있게 구분하라
- `a1, a2, a3`
    - 연속적으로 숫자를 덧붙힌 이름은 사용하지 않는다
- `ProductInfo`, `ProductData`
    - Info나 Data는 의미가 불분명한 용어이다
- 읽는 사람이 차이를 알 수 있도록 이름을 지어라
    - `Customer`,`CustomerObject`
    - `money`,`moneyAmount`
    - `message`,`theMessage`


## 발음하기 쉬운 이름을 사용하라
- 발음하기 어려운 이름은 토론하기도 어렵다
```
// Bad
class DtaRcrd102 {
    private Date genymdhms;
    private Date modymdhms;
    private final String pszqint = "102";
    /* ... */
}

// Good
class Customer {
    private Date generationTimestamp;
    private Date modificationTimestamp;
    private final String recordId = "102";
    /* ... */
}
```

## 검색하기 쉬운 이름을 사용하라
- 숫자 7, 영어 e등은 이름으로 적합하지않다
    - 검색하게되면 7 또는 e가 들어가는 파일명, 수식이 많아 찾기 힘들다
    - MAX_CLASSES_PER_STUDENT 와 같이 숫자의 의미를 알려주고 검색을 쉽게 해야한다
- 이름의 길이는 범위 크기에 비례해야한다


## 인코딩을 피하라
- 인코딩한 이름은 발음이 어려우며 오타가 생길 수 있으니 피하자

### 헝가리식 표기법
[헝가리안 표기법](https://namu.wiki/w/%ED%97%9D%EA%B0%80%EB%A6%AC%EC%95%88%20%ED%91%9C%EA%B8%B0%EB%B2%95#toc)이라고도 한다
요즘은 컴파일러가 점검하므로 불필요하지만 
이전에는 컴파일러가 타입을 기억할 단서가 필요하였다

### 멤버 변수 접두어
멤버변수에 m_ 이라는 접두어를 붙일 필요가 없다

### 인터페이스 클래스와 구현 클래스
인터페이스 클래스와 구현클래스가 나뉜다면 구현클래스 이름에 정보를 인코딩 한다
```
// Bad
interface IShapeFactory {}
class ShapeFactory {}

// Good
interface ShapeFactory {}
class ShpaeFactoryImpl {} 
```


## 자신의 기억력을 자랑하지 마라
문자하나만 사용하늠 변수이름을 정할때
반복문에서는 전통적으로 한글자 변수를 사용하여서 괜찮지만 그 이외에는 대부분 부적절하다.
또한 반복문에서 `i`,`j`,`k`등은 괜찮지만 `l`은 절대로 안된다  


## 클래스 이름
- 클래스 이름과 객체 이름은 명사나 명사구가 적합하다
- Manager, Processor, Data, Info등과 같은 단어는 되도록 피한다
- 동사를 사용하지 않는다


## 메서드 이름
- 동사나 동사구를 사용한다
    - `postPayment`, `deletePage`, `save`
- 접근자, 변경자, 조건자는 표준에따라 get, set, is를 붙힌다
- 생성자를 오버로딩 할때는 정적 팩토리 메서드를 사용한다
    - 메서드는 인수를 설명하는 이름을 사용한다
    ```
    // Bad
    Complex fulcrumPoint = new Complex(23.0);

    // Good
    Complex fulcrumPoint = Conplex.FromRealNumber(23.0);
    ```


## 기발한 이름은 피하라
- 자신들만 아는 은어나 속어를 사용하지 않는다.
- 모두가 알만한 명료한 이름을 선택하라


## 한 개념에 한 단어를 사용하라
- 똑같은 메서드를 클래스마다 fetch, get, retrieve 로 나누어 쓰지 않아야한다
- 코드 기반에서 controller, manager, driver를 섞어 쓰지않아야한다
- 이름이 다르다면 코드를 읽는사람은 당연하게 클래스도 다르고 타입도 다를것이라 생각하게된다


## 말장난을 하지마라
같은 맥락이 아닌데도 `일관성`을 고려하여서 add라는 단어를 선택하거나 하는 말장난은 하면 안된다  
맥락을 고려하여 insert나 append를 사용하는것이 적당하다
```
public int add(int a, int b){
    return a + b; 
}

public void add(int a){
    list.add(a);
}
```


## 해법 영역에서 가져온 이름을 사용하라
- 프로그래머용어, 전산용어, 알고리즘 이름, 패턴 이름, 수학 용어 등을 사용하여도 좋다 (ex : JobQueue)


## 문제 영역에서 가져온 이름을 사용하라
- 프로그래머용어, 전산용어, 알고리즘 이름, 패턴 이름, 수학 용어등이 없다면 문제영역의 이름을 사용한다
- 해법영역과 문제영역을 구분한 줄 알아야한다


## 의미 있는 맥락을 추가하라
클래스, 함수 , 이름공간(NameSpace)에 맥락을 표현한다
그래도 표현할 수 없다면 접두어를 붙힌다

클래스에 `fistName`, `lastName`, `street`, `houseNumber`, `city`, `state` ,`zipcode`라는 변수가 있다면 
주소 일부라는 사실을 알 수 있지만 예를들어 하나의 변수 `state`만 있다면 주소인지를 유추하기 쉽지않다.
이때 `addr`이라는 접두으럴 추가해 `addrFirstName`, `addrLastName`, `addrState`라 쓰면 
맥락이 명확해진다.

```
/ Bad
private void printGuessStatistics(char candidate, int count) {
    String number;
    String verb;
    String pluralModifier;
    if (count == 0) {
        number = "no";
        verb = "are";
        pluralModifier = "s";
    }  else if (count == 1) {
        number = "1";
        verb = "is";
        pluralModifier = "";
    }  else {
        number = Integer.toString(count);
        verb = "are";
        pluralModifier = "s";
    }
    String guessMessage = String.format("There %s %s %s%s", verb, number, candidate, pluralModifier );

    print(guessMessage);
}

/ Good
public class GuessStatisticsMessage {
    private String number;
    private String verb;
    private String pluralModifier;

    public String make(char candidate, int count) {
        createPluralDependentMessageParts(count);
        return String.format("There %s %s %s%s", verb, number, candidate, pluralModifier );
    }

    private void createPluralDependentMessageParts(int count) {
        if (count == 0) {
            thereAreNoLetters();
        } else if (count == 1) {
            thereIsOneLetter();
        } else {
            thereAreManyLetters(count);
        }
    }

    private void thereAreManyLetters(int count) {
        number = Integer.toString(count);
        verb = "are";
        pluralModifier = "s";
    }

    private void thereIsOneLetter() {
        number = "1";
        verb = "is";
        pluralModifier = "";
    }

    private void thereAreNoLetters() {
        number = "no";
        verb = "are";
        pluralModifier = "s";
    }
}
```


## 불필요한 맥락을 없애라
GSD 라는 어플리캐이션을 만들 때 GSDAccountAddress와 같이 작성하면 너무 불필요한 단어가 많이 포함된다
맥락이 잘 나누어져 있다면 Address로 충분하다