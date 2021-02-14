# Chapter 12 - 창발성

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 12 - 창발성](#chapter-12-창발성)
  - [창발적 설계로 깔끔한 코드를 구현하자](#창발적-설계로-깔끔한-코드를-구현하자)
  - [단순한 설계규칙 1: 모든 테스트를 실행하라](#단순한-설계규칙-1-모든-테스트를-실행하라)
  - [단순한 설계 규칙 2~4 : 리펙터링](#단순한-설계-규칙-2~4-리펙터링)
  - [중복을 없애라](#중복을-없애라)
  - [표현하라](#표현하라)
  - [클래스와 메서드 수를 최소로 줄여라](#클래스와-메서드-수를-최소로-줄여라)
  - [결론](#결론)

<!-- /code_chunk_output -->

## 창발적 설계로 깔끔한 코드를 구현하자
착실하게 따르기만 하면 우수한 설계가 나오는 간단한 규칙 네가지가 있다.
규칙을 지키면 코드 구조와 설계 파악을 하기 쉽고, SRP나 DIP같은 클래스 설계 원칙을 적용하기 쉬워진다.

켄트백이 제시한 **단순한 설계** 규칙
- 모든 테스트를 실행한다
- 중복을 없앤다
- 프로그래머 의도를 표현한다
- 클래스와 메서드 수를 최소로 줄인다

위 목록은 중요도 순이다

## 단순한 설계규칙 1: 모든 테스트를 실행하라
**테스트 케이스를 만들고 계속 돌려라** 라는 간단하고 단순한 규칙을 따르면
시스템은 낮은 결합도와 높은 응집력 이라는, 객체지향 방법이 지향하는 목표를 저절로 달성한다.

1. 무엇보다 먼저, 설계는 의도한 대로 돌아가는 시스템을 내놓아야 한다
    - 완벽하게 시스템을 설계하여도 먼저 동작을 하여야 검증이 가능하다
2. 테스트를 철저히 거쳐 모든 테스트 케이스를 항상 통과하는 시스템은 '테스트가 가능한 시스템이다'
    - 당연한 말이지만 중요하다. **테스트가 불가능한 시스템은 검증도 불가능하다**
3. 테스트가 가능한 시스템을 만들려고 애쓰면 설계 품질이 높아진다
    - 크기가 작고 하나만 수행하는 클래스가 나온다
    - 테스트 케이스가 많을수록 테스트가 쉽게 코드를 작성한다
4. 결합도가 높으면 테스트를 작성하기 어렵다
    - 테스트 케이스를 많이 작성할수록 개발자는 DIP같은 원칙을 적용하고, 의존성 주입<sup>Dependency Injection</sup>, 인터페이스, 추상화 등과 같은 도구를 사용하여 결합도를 낮춘다


## 단순한 설계 규칙 2~4 : 리펙터링
테스트 케이스를 모두 작성하였다면 코드와 클래스를 정리해도 괜찮다.
깔끔히 정리한 후 테스트를 돌려 기존 기능을 깨뜨리지 않았다는 사실을 확인한다.

리펙터링 단계에서는 설계품질을 높이는 모든 기법을 적용해도 좋다
- 응집도 높이기
- 결합도 낮추기
- 관심사 분리하기
- 시스템 관심사를 모듈로 나누기
- 함수와 클래스 크기를 줄이기
- 더 나은 이름을 선택하기
- 중복 제거
- 프로그래머 의도 표현
- 클래스와 메서드 수를 최소로 줄이는 단계


## 중복을 없애라
우수한 설계에서 중복은 커다란 적이다.
중복은 추가 작업, 추가 위험, 불필요한 복잡도를 뜻한다.


중복의 한 형태
```
// Bad
int size() {}
boolean isEmpty{}
```
```
// Good
boolean isEmpty() {
  return 0 == size();
}
```
각 메서드를 따로 구현하는 방법도 있다. 하지만 `size()` 가 개수를 반환하는 로직이기에, `isEmpty` 는 이를 이용하면 코드를 중복해서 구현할 필요가 없어진다.
깔끔한 시스템을 만들려면 단 몇 줄이라도 중복을 제거하겠다는 의지가 필요하다.


```
// Bad
public void scaleToOneDimension(float desiredDimension, float imageDimension) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold)
    return;
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);

  RenderedOp newImage = ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor);
  image.dispose();
  System.gc();
  image = newImage;
}

public synchronized void rotate(int degrees) {
  RenderedOp newImage = ImageUtilities.getRotatedImage(image, degrees);
  image.dispose();
  System.gc();
  image = newImage;
}
```
`scaleToOnDimension` 메서드와 `rotate` 메서드를 살펴보면 일부 코드가 동일하다.

```
// Good
public void scaleToOneDimension(float desiredDimension, float imageDimension) {
  if (Math.abs(desiredDimension - imageDimension) < errorThreshold)
    return;
  float scalingFactor = desiredDimension / imageDimension;
  scalingFactor = (float) Math.floor(scalingFactor * 10) * 0.01f);
  replaceImage(ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor));
}

public synchronized void rotate(int degrees) {
  replaceImage(ImageUtilities.getRotatedImage(image, degrees));
}

private void replaceImage(RenderedOp newImage) {
  image.dispose();
  System.gc();
  image = newImage;
}
```
아주 적은 양이지만 공통적인 코드를 새 메서드로 뽑고 보니 클래스가 SRP를 위반한다. 그러므로 새로 만든 `replaceImage` 메서드를 다른 클래스로 옮겨도 좋겠다.

- 새 메서드의 가시성이 높아진다
- 다른 팀원이 새 메서드를 좀 더 추상화해 다른 맥락에서 재사용할 기회를 포착 할 수도 있다
이런 '소규모 재사용'은 시스템 복잡도를 극적으로 줄여준다. 



다른 예로 [TEMPLATE METHOD](https://beomseok95.tistory.com/245?category=1066005) 패턴을 사용하여 고차원 중복을 제거하는 예시를 살펴본다
```
public class VacationPolicy {
  public void accrueUSDDivisionVacation() {
    // 지금까지 근무한 시간을 바탕으로 휴가 일수를 계산하는 코드
    // ...
    // 휴가 일수가 미국 최소 법정 일수를 만족하는지 확인하는 코드 
    // ...
    // 휴가 일수를 급여 대장에 적용하는 코드
    // ...
  }
  
  public void accrueEUDivisionVacation() {
    // 지금까지 근무한 시간을 바탕으로 휴가 일수를 계산하는 코드
    // ...
    // 휴가 일수가 유럽연합 최소 법정 일수를 만족하는지 확인하는 코드
    // ...
    // 휴가 일수를 급여 대장에 적용하는 코드
    // ...
  }
}
```
최소 법정 일수를 계산하는 코드만 제외하면 두 메서드는 거의 동일하다.
```
abstract public class VacationPolicy {
  public void accrueVacation() {
    caculateBseVacationHours();
    alterForLegalMinimums();
    applyToPayroll();
  }
  
  private void calculateBaseVacationHours() { /* ... */ };
  abstract protected void alterForLegalMinimums();
  private void applyToPayroll() { /* ... */ };
}

public class USVacationPolicy extends VacationPolicy {
  @Override protected void alterForLegalMinimums() {
    // 미국 최소 법정 일수를 사용한다.
  }
}

public class EUVacationPolicy extends VacationPolicy {
  @Override protected void alterForLegalMinimums() {
    // 유럽연합 최소 법정 일수를 사용한다.
  }
}
```


## 표현하라
자신이 이해하는 코드를 짜기는 쉽다. 하지만 나중에 코드를 유지보수할 사람이 그만큼 문제를 깊이 이해할 가능성은 희박하다.

1. 좋은 이름을 선택한다
    - 이름과 기능이 완전 따로 노는 클래스나 함수를 사용해선 안된다.
2. 함수와 클래스 크기를 가능한 한 줄인다
    - 작은 클래스와 작은 함수는 이름 짓기도 쉽고, 구현하기도 쉬우며 이해하기도 쉽다.
3. 표준 명칭을 사용한다
    - 클래스가 COMMAND와 VISITOR같은 표준 패턴을 사용해 구현된다면 클래스 이름에 패턴 이름을 넣어준다.
4. 단위 테스트 케이스를 꼼꼼히 작성한다.
    - 테스트 케이스는 소위 '예제로 보여주는 문서'다. 다시 말해, 잘 만든 테스트 케이스를 읽어보면 클래스 기능이 한눈에 들어온다.

표현력을 가장 높이는 가장 중요한 방법은 **노력**이다.
나중에 읽을 사람을 고려하여 조금이라도 읽기 쉽게 만드려는 충분한 고민을 하자.


## 클래스와 메서드 수를 최소로 줄여라
중복을 제거하고, 의도를 표현하고, SRP를 준수한다는 기본적인 개념도 극단으로 치달으면 득보다 실이 많아진다. 클래스와 메서드 크기를 줄이자고 조그만 클래스와 메서드를 수없이 만드는 사례도 없지 않다. 그래서 이 규칙은 함수와 클래스 수를 가능한 한 줄이라고 제안한다.


## 결론
이 책에서 소개하는 기법은 저자들이 수십 년 동안 쌓은 경험의 정수다. 단순한 설계 규칙을 따른다면 (오랜 경험 후에야 익힐) 우수한 기법과 원칙을 단번에 활용할 수 있다.