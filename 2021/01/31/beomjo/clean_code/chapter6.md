# Chapter6 - 객체와 자료 구조 

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter6 - 객체와 자료 구조](#chapter6-객체와-자료-구조)
  - [자료 추상화](#자료-추상화)
    - [6-3과 6-4는 필요에 따라 메서드 구현이 달라지는게 아닌가?](#6-3과-6-4는-필요에-따라-메서드-구현이-달라지는게-아닌가)
  - [자료/객체 비대칭](#자료객체-비대칭)
  - [디미터 법칙](#디미터-법칙)
    - [기차 충돌](#기차-충돌)
    - [간결하고 깔끔해 보이지 않는가](#간결하고-깔끔해-보이지-않는가)
    - [잡종 구조](#잡종-구조)
    - [구조체 감추기](#구조체-감추기)
  - [자료 전달 객체](#자료-전달-객체)
  - [결론](#결론)

<!-- /code_chunk_output -->


## 자료 추상화
```
// 6-1
public class Point {
    public double x;
    public double y;
}

// 6-2
public interface Point {
    double getX();
    double getY();
    void setCartesian(double x, double y);
    double getR();
    double getTheta();
    void setPolar(double r, double theta);
}
```
- 6-1은 구현을 노출한다. 변수를 private으로 선언 하더라도 각 값마다 조회 함수와 설정 함수를 제공한다면 구현을 외부로 노출하는 셈이다.
- 6-2는 메서드가 접근 정책을 강제한다. 좌표를 읽을 때는 각 값을 개별적으로, 설정 할 때는 두 값을 한꺼번에 설정 해야 한다.

 ### 6-2도 getter, setter 있는데 노출 하는거 아닌가?
```
// 6-3
public interface Vehicle {
    double getFuelTankCapacityInGallons();
    double getGallonsOfGasoline();
}

// 6-4
public interface Vehicle {
    double getPercentFuelRemaining();
}
```
- 6-3은 자동차 연료 상태를 구체적인 숫자 값으로 알려준다.
- 6-4는 자동차 연료 상태를 백분율이라는 추상적인 개념으로 알려준다

### 6-3과 6-4는 필요에 따라 메서드 구현이 달라지는게 아닌가?

- 6-1과 6-2에서는 6-2가, 6-3과 6-4에서는 6-4가 더 좋다.
- 인터페이스나 조회/설정 함수만으로는 추상화가 이뤄지지 않는다. 아무 생각 없이 조회/설정 함수를 추가하는 방법이 가장 나쁘다.


## 자료/객체 비대칭 
- 객체는 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개한다.
- 자료 구조는 자료를 그대로 공개하며 별다른 함수는 제공하지 않는다.

```
// 6-5
public class Square {
    public Point topLeft;
    public double side;
}
public class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}
public class Circle {
    public Point center;
    public double radius;
}
public class Geometry {
    public final double PI = 3.141592653589793;
    public double area(Object shape) throws NoSuchShapeException{
        if (shape instanceOf Square) {
            Square s = (Square)shape;
            return s.side * s.side;
        } else if (shape instanceOf Rectangle) {
            Rectangle r = (Rectangle)shape;
            return r.height * r.width;
        } else if (shape instanceOf Circle) {
            Circle c = (Circle)shape;
            return PI * c.radius * c.radius;
        } 
        throw new NoSuchShapeException();
    }
}

// 6-6
public class Square implements Shape {
    private Point topLeft;
    private double side;
    public double area() {
        return side*side;
    }
}

public class Rectangle implements Shape {
    private Point topLeft;
    private double height;
    private double width;
    public double area() {
        return height * width;
    }
}

public class Circle implements Shape {
    private Point center;
    private double radius;
    public final double PI = 3.141592653589793;
    public double area() {
        return PI * radius * radius;
    }
}
```
- 절차적인 6-5는 새로운 함수를 추가할 경우 도형 클래스와 도형 클래스에 의존하는 다른 클래스 모두 영향을 받지 않는다. 반면 새 도형을 추가하게 되면 Geometry 클래스에 속한 함수를 모두 고쳐야 한다. 
- 객채 지향적인 6-6의 경우 Geometry 클래스는 필요 없다. 그러므로 새 도형을 추가해도 기존 함수에 아무런 영향을 미치지 않는다. 반면 새 함수를 추가하고 싶다면 도형 클래스 전부를 고쳐야 한다.
- 따라서 객체와 자료 구조는 근본적으로 양분 된다.
- 분별 있는 프로그래머는 모든 것이 객체라는 생각이 __미신__ 임을 잘 안다. 때로는 단순한 자료 구조와 절차적인 코드가 가장 적합한 상황도 있다.


## 디미터 법칙
- 모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙이다. 즉, 객체는 조회 함수로 내부 구조를 공개하면 안 된다.
- 좀 더 정확히 표현하자면, 디미터 법칙은 "클래스 C와 메서드 f는 다음과 같은 객체의 메서드만 호출해야 한다"고 주장한다.
    - 클래스 C
    - f가 생성한 객체
    - f 인수로 넘어온 객체
    - C 인스턴스 변수에 저장된 객체
```
final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();
```
- 위의 코드는 디미터 법칙을 어기는 듯이 보인다.

### 기차 충돌
- 흔히 위와 같은 코드를 기차 충돌이라 부른다.
- 일반적으로 조잡하다 여겨지는 방식이므로 피하는 편이 좋다.

### 간결하고 깔끔해 보이지 않는가
```
Options opts = ctxt.getOptions();
File scratchDir = opts.getScratchDir();
final String outputDir = scratchDir.getAbsolutePath();
```
- 위 예제가 디미터 법칙을 위반하는지 여부는 ctxt, Options, ScratchDir이 객체인지 아니면 자료 구조인지에 달렸다.

### 잡종 구조
- 때때로 절반은 객체, 절반은 자료 구조인 잡종 구조가 나온다.
- 이런 잡종 구조는 새로운 함수는 물론이고 새로운 자료 구조도 추가하기 어려운 양쪽 세상에서 단점만 모이놓은 구조다.

### 구조체 감추기
```
ctxt.getAbsolutePathOfScratchDirectoryOption();

ctx.getScratchDirectoryOption().getAbsolutePath()
``` 
- 첫 번째 방법은 ctxt 객체에 공개해야 하는 메서드가 너무 많아진다.
- 두 번째 방법은 getScratchDirectoryOption()이 객체 아니라 자료 구조를 반환한다고 가정한다.
```
String outFile = outputDir + "/" + className.replace('.', '/') + ".class";
FileOutputStream fout = new FileOutputStream(outFile);
BufferedOutputStream bos = new BufferedOutputStream(fout);
```
- ctxt가 객체라면 __뭔가를 하라고__ 말해야지 속을 드러내라고 말하면 안된다.
- 절대 경로가 필요한 이유를 보니 임시 파일을 생성하기 위한 목적이라는 사실이 드러난다.
```
BufferedOutputStream bos = ctxt.createScratchFileStream(classFileName);
```
- ctxt 객체에 임시 파일을 생성하라고 시키면 내부 구조를 드러내지 않으며, 모듈에서 해당 함수는 자신이 몰라야 하는 여러 객체를 탐색할 필요가 없다.
- 따라서 디미터 법칙을 위반하지 않는다.


## 자료 전달 객체
- 자료 구조체의 전형적인 형태는 공개 변수만 있고 함수가 없는 클래스다.
- 이런 자료 구조체를 때로는 자료 전달 객체<sup>Data Transfer Object. DTO</sup>라 한다. 
- 좀 더 일반적인 형태는 '빈<sup>bean</sup>' 구조다.
```
public class Address {
    private String street;
    private String streetExtra;
    private String city;
    private String state;
    private String zip;
    
    public Address(String street, String streetExtra,
        String city, String state, String zip) {
        this.street = street;
        this.streetExtra = streetExtra;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetExtra() {
        return streetExtra;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
```

## 결론
- 시스템을 구현할 때, 새로운 자료 타입을 추가하는 유연성이 필요하면 객체가 더 적합하다.
- 다른 경우로 새로운 동작을 추가하는 유연성이 필요하면 자료 구조와 절차적인 코드가 더 적합하다.
- 우수한 소프트웨어 개발자는 편견 없이 이 사실을 이해해 직면한 문제에 최적인 해결책을 선택한다.