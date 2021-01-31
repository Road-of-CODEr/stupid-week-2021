# Chapter 7 - 오류처리


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 7 - 오류처리](#chapter-7-오류처리)
  - [오류 코드보다 예외를 사용하라](#오류-코드보다-예외를-사용하라)
  - [Try-Catch-Finally 문부터 작성하라](#try-catch-finally-문부터-작성하라)
  - [미확인 예외를 사용하라](#미확인-예외를-사용하라)
  - [예외에 의미를 제공하라](#예외에-의미를-제공하라)
  - [호출자를 고려해 예외 클래스를 정의하라](#호출자를-고려해-예외-클래스를-정의하라)
  - [정상 흐름을 정의하라](#정상-흐름을-정의하라)
  - [null을 반환하지 마라](#null을-반환하지-마라)
  - [null을 전달하지 마라](#null을-전달하지-마라)
  - [요약](#요약)

<!-- /code_chunk_output -->

## 오류 코드보다 예외를 사용하라
이전에는 예외를 지원하지않는 프로그래밍 언어가 많았다.
그래서 오류 플래그를 설정하거나 호출자에게 오류코드를 반환하였다.

```
// Bad
이와 같은 방법은 함수를 호출한 즉시 오류를 확인해야 하기 때문에 잊어버리기 쉽다
// 
public class DeviceController {
  ...
  public void sendShutDown() {
    DeviceHandle handle = getHandle(DEV1);
    if (handle != DeviceHandle.INVALID) {
      retrieveDeviceRecord(handle);
      if (record.getStatus() != DEVICE_SUSPENDED) {
        pauseDevice(handle);
        clearDeviceWorkQueue(handle);
        closeDevice(handle);
      } else {
        logger.log("Device suspended. Unable to shut down");
      }
    } else {
      logger.log("Invalid handle for: " + DEV1.toString());
    }
  }
  ...

// Good
public class DeviceController {
  ...
  public void sendShutDown() {
    try {
      tryToShutDown();
    } catch (DeviceShutDownError e) {
      logger.log(e);
    }
  }
    
  private void tryToShutDown() throws DeviceShutDownError {
    DeviceHandle handle = getHandle(DEV1);
    DeviceRecord record = retrieveDeviceRecord(handle);
    pauseDevice(handle); 
    clearDeviceWorkQueue(handle); 
    closeDevice(handle);
  }
  
  private DeviceHandle getHandle(DeviceID id) {
    ...
    throw new DeviceShutDownError("Invalid handle for: " + id.toString());
    ...
  }
  ...
}
}
```
디바이스를 종료하는 알고리즘과 오류를 처리하는 알고리즘을 분리하여
각 개념을 독립적으로 살펴보고 이해할 수 있다.


## Try-Catch-Finally 문부터 작성하라
try 블록은 트랜잭션과 비슷하여, try 블록에서 무슨일이 생기던지
catch 블록은 프로그램 상태를 일관성 있게 유지해야한다.
예외를 발생할 코드를 짤 때는 `try-catch-finally`문으로 시작하는것이 낫다. 


파일이 없으면 예외를 던지는지 알아보는 단위 테스트
```
// 테스트 작성 - StorageException이 발생하지않아 테스트 실패
 @Test(expected = StorageException.class)
public void retrieveSectionShouldThrowOnInvalidFileName() {
  sectionStore.retrieveSection("invalid - file");
}

public List<RecordedGrip> retrieveSection(String sectionName) {
    return new ArrayList<RecordedGrip>();
}
```

```
// retrieveSection 구현 변경 - 테스트 통과
 public List<RecordedGrip> retrieveSection(String sectionName) {
    try {
      FileInputStream stream = new FileInputStream(sectionName)
    } catch (Exception e) {
      throw new StorageException("retrieval error", e);
    }
    return new ArrayList<RecordedGrip>();
  }
```

```
// 추가 리펙터링
// catch 블록에서 예외 유형을 좁혀 실제로 FileInputStream 생성자가 던지는 FileNotFoundException을 체크
public List<RecordedGrip> retrieveSection(String sectionName) {
    try {
      FileInputStream stream = new FileInputStream(sectionName);
      stream.close();
    } catch (FileNotFoundException e) {
      throw new StorageException("retrieval error", e);
    }
    return new ArrayList<RecordedGrip>();
  }

```
먼저 강제로 예외를 일으키는 테스트케이스를 작성한 후
테스트를 통화하게 코드를 먼저 작성하는 방법을 권장(TDD)
그러면 자연스럽게 try 블록의 트랜잭션 범위부터 구현하게 되므로 범위 내에서 트랜잭션 본질을 유지하기 쉬워진다.


## 미확인 예외를 사용하라
자바 첫 버전에서는 메서드를 선언할 때 메서드가 반환할 예외를 모두 열거햇다.(확인된 예외)

확인된 예외는 OCP를 위반한다
메소드에서 확인된 예외를 던졌는데 catch블럭이 세 단계 위에 있다면
그 사이 메서드가 모두 선언부에 해당 예외를 정의해야 한다.(하위단계에서 코드를 변경하면 상위단계도 모두 코드를 고쳐야함)
이는 하위단계에서 쓰이는 예외를 상위단계에서도 알아야함으로 캡슐화도 깨지게된다.

아주 중요한 라이브러러리를 작성한다면 확인된 예외를 작성하는것도 좋다.
하지만 일반적인 애플리케이션은 의존성이라는 비용이 이익보다 크다.


## 예외에 의미를 제공하라
예외를 던질때는 전후 상황을 충분히 덧붙힌다. 오류를 발생한 원인과 위치를 찾기 쉬워진다.
- 오류 메세지에 정보를 담아 예외와 함께 던진다
- 실패한 연산 이름과 실패 유형도 언급한다.
- 애플리케이션이 로깅 기능을 사용한다면 catch블록에서 오류를 기록하도록 충분한정보를 넘겨준다.


## 호출자를 고려해 예외 클래스를 정의하라
오류를 분류하는 방법은 수없이 많다. 
오류가 발생한  위치(컴포넌트로 분류), 오류의 유형 (디바이스 실패, 네트워크 실패, 프로그래밍 오류)등등

하지만 오류를 정의할 때 중요한 것은 **오류를 잡아내는 방법**이다.
```
// Bad
// 외부 라이브러리를 호출하는 try-catch-finally 문을 포함한 코드로 
// 외부 라이브러리가 던질 예외를 모두 잡아낸다.
ACMEPort port = new ACMEPort(12);
try {
  port.open();
} catch (DeviceResponseException e) {
  reportPortError(e);
  logger.log("Device response exception", e);
} catch (ATM1212UnlockedException e) {
  reportPortError(e);
  logger.log("Unlock exception", e);
} catch (GMXError e) {
  reportPortError(e);
  logger.log("Device response exception");
} finally {
  ...
}


// Good
LocalPort port = new LocalPort(12);
  try {
    port.open();
  } catch (PortDeviceFailure e) {
    reportError(e);
    logger.log(e.getMessage(), e);
  } finally {
    ...
  }
  
  public class LocalPort {
    private ACMEPort innerPort;
    public LocalPort(int portNumber) {
      innerPort = new ACMEPort(portNumber);
    }
    
    public void open() {
      try {
        innerPort.open();
      } catch (DeviceResponseException e) {
        throw new PortDeviceFailure(e);
      } catch (ATM1212UnlockedException e) {
        throw new PortDeviceFailure(e);
      } catch (GMXError e) {
        throw new PortDeviceFailure(e);
      }
    }
    ...
  }

```
LocalPort 클래스처럼 ACMEPort를 감싸는 클래스는 매우 유용하다
- 의존성이 크게 줄어든다
- 다른 라이브러리로 갈아타는 비용이 적어진다
- 감싸는 클래스에서 외부 API를 호출하는 대신 테스트코드를 넣어주는 방법으로 프로그램을 테스트하기 쉬워진다
- 특정 업체가 API를 설계한 방식에 발목잡히지 않는다


## 정상 흐름을 정의하라
위 처럼 외부 API 를 감싸 독자적인 예외를 던지고 코드 위에 처리기를 정의해 중단된 계산을 처리한다.
대게는 괜찮지만, 때로는 중단이 적합하지 않는 때도 있다.
```
// Bad
  try {
    MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
    m_total += expenses.getTotal();
  } catch(MealExpensesNotFound e) {
    m_total += getMealPerDiem();
  }

// Good
public class PerDiemMealExpenses implements MealExpenses {
    public int getTotal() {
      // return the per diem default
    }
}
------------------------------------------------------------------------
// Good UseCase
try {
    expenses = expenseReportDAO.getMeals(employee.getID());
} catch(MealExpensesNotFound e) {
    expenses = new PerDiemMealExpenses();
} 
```
이와 같은 경우를 특수 사례패턴(SPECIAL CASE PATTERN)이라 한다.
클래스를 만들거나 객체를 조작해 특수 사례를 처리하는 방식이다.
클라이언트가 예외적인 상황을 처리할 필요가 없어진다(클래스나 객체가 예외적인 상황을 캡슐화 하여 처리)


## null을 반환하지 마라
안좋은 습관중 하나인 오류 발생시 null을 반환하는 행위이다.
null을 반환하는 행위는 일거리를 늘릴 뿐 아니라 호출자에게 문제를 떠 넘기는 행위이다.
누구하나라도 null check 를 빼먹는다면 애플리케이션이 문제가 발생할 것이다.
```
// Bad
 public void registerItem(Item item) {
    if (item != null) {
      ItemRegistry registry = peristentStore.getItemRegistry();
      if (registry != null) {
        Item existing = registry.getItem(item.getID());
        if (existing.getBillingPeriod().hasRetailOwner()) {
          existing.register(item);
        }
      }
    }
  }
```
```
  // Bad
  List<Employee> employees = getEmployees();
  if (employees != null) {
    for(Employee e : employees) {
      totalPay += e.getPay();
    }
  }

  // Good
  List<Employee> employees = getEmployees();
  for(Employee e : employees) {
    totalPay += e.getPay();
  }
  
  public List<Employee> getEmployees() {
    if( .. 직원이 없다면 .. )
      return Collections.emptyList();
    }
```
null반환이 아닌 빈리스트를 반환하는 케이스


## null을 전달하지 마라
메서드에서 null을 반환하는 방식도 나쁘지만 메소드로 null을 전달하는 방식은 더 나쁘다.
```
// Bad -1
public class MetricsCalculator {
  public double xProjection(Point p1, Point p2) {
    return (p2.x – p1.x) * 1.5;
  }
  ...
}

// Bad -2
public class MetricsCalculator {
  public double xProjection(Point p1, Point p2) {
    if(p1 == null || p2 == null){
      throw InvalidArgumentException("Invalid argument for MetricsCalculator.xProjection");
    }
    return (p2.x – p1.x) * 1.5;
  }
}
```
Bad -1은 당연히 NPE가 발생한다
Bad -2는 조금 낫지만 InvalidArgumentException에 대한 처리가 또 필요하다

또 다른 방법으로는 assert를 활용할 수도 있다
```
public class MetricsCalculator {
  public double xProjection(Point p1, Point p2) {
    assert p1 != null : "p1 should not be null";
    assert p2 != null : "p2 should not be null";
    
    return (p2.x – p1.x) * 1.5;
  }
}
```
문서화가 잘 되어있지만 결국 누군가 null을 전달하면 여전히 오류가 발생한다

대다수 프로그래밍 언어는 호출자가 실수로 넘기는 null을 적절하게 처리하는 방법이없다.
그렇다면 애초에 null을 넘기지 못하도록 금지하는 정책이 합리적이다.


## 요약
깨끗한 코드는 읽기도 좋아야 하지만 안정성도 높아야 한다.
오류처리(예외처리)를 비즈니스 로직과 분리해 독자적인 사안으로 고려하면 튼튼하고 깨끗한 코드를 작성할 수 있다.
또한 독립적인 추론이 가능해져 코드 유지보수성도 높아진다.