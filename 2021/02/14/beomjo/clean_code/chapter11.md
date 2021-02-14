
<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 11 - 시스템](#chapter-11-시스템)
  - [도시를 세운다면?](#도시를-세운다면)
  - [시스템 제작과 시스템 사용을 분리하라](#시스템-제작과-시스템-사용을-분리하라)
    - [Main 분리](#main-분리)
    - [팩토리](#팩토리)
    - [의존성 주입](#의존성-주입)
  - [확장](#확장)
    - [횡단(cross-cutting) 관심사](#횡단cross-cutting-관심사)
  - [자바 프록시](#자바-프록시)
  - [순수 자바 AOP 프레임워크](#순수-자바-aop-프레임워크)
  - [AssertJ 관점](#assertj-관점)
  - [테스트 주도 시스템 아키텍쳐 구축](#테스트-주도-시스템-아키텍쳐-구축)
  - [의사 결정을 최적화 하라](#의사-결정을-최적화-하라)
  - [명백한 가치가 있을 때 표준을 현명하게 사용하라](#명백한-가치가-있을-때-표준을-현명하게-사용하라)
  - [시스템 도메인 특화 언어가 필요하다.](#시스템-도메인-특화-언어가-필요하다)

<!-- /code_chunk_output -->

## 도시를 세운다면?
도시를 세운다면 온갖 세세한 사항을 혼자서 직접 관리할 수 있을까?
일반적으로 도시는 수도관리팀, 전력관리팀, 교통관리팀, 치안관리팀.. 등 각 분야를 관리하는 팀이 있다.
도시에는 큰 그림을 그리는 사람도 있으며, 작은 사항에 집중하는 사람들도 있다.

도시가 돌아가는 또 다른 이유는 적절한 추상화와 모듈화 때문이다.
그래서 큰 그림을 이해하지 못할지라도 개인과 개인이 괄니하는 `구성요소`는 효율적으로 돌아간다

소프트웨어도 도시와 같다.


## 시스템 제작과 시스템 사용을 분리하라
제작<sup>construction</sup>과 사용<sup>use</sup>는 아주 다르다는 사실을 명심한다.

소프트웨어 시스템은 (애플리케이션 객체를 제작하고, 의존성을 서로 연결하는) 준비 과정과
(준비 과정 이후에 이어지는) 런타임 로직을 분리해야 한다.

**관심사 분리**는 우리 분야에서 가장 오래되고 가장 중요한 설계 기법중 하나이다.

코드로 알아보자
```
public Service getService() {
  if (service == null) {
    service = new MyServiceImpl(...);
  }
  return service;
}
```
위는 초기화지연<sup>Lazy Initialization</sup> 혹은 계산 지연<sup>Lazy Evaluation</sup> 이라는 기법이다.
장점
- 실제로는 필요할 때 까지 객체를 생성하지 않으므로 불필요한 부하가 걸리지않는다.
- 어떤 경우에도 null 포인터를 반환하지 않는다

하지만 단점은
- `getService` 메서드가 `MyServiceImpl`의 생성자에 의존한다.
    - 런타임 로직에서 `MyServiceImpl` 객체를 전혀 사용하지 않더라도 의존성 문제가 생겨 컴파일이 불가능 하다
- 테스트가 힘들다
    - `getService`를 호출하기 전에 적절한 테스트 전용객체(Mock)을 service에 할당해야한다
    - 런타임 로직에 객체 생성로직을 섞어놓은 탓에 (service가 null인 경로와 null이 아닌경로) 모든 실행 경로도 테스트해야한다
- 책임이 한가지 이상이다
    - 책임이 한가지 이상이라는 것은 메서드가 두가지 이상을 수행한다는 의미
    - SRP을 위반한다
- `MyServiceImpl`이 모든 상황에서 적합한 객체인지 알 수 없다

### Main 분리
시스템 생성과 시스템 사용을 분리하는 한 가지 방법으로, 생성과 관련한 코드는 모두 main이나 main이 호출하는 모듈로 옮기고, 나머지 시스템은 모든 객체가 생성되었고 모든 의존성이 연결되었다고 가정한다.
즉 애플리케이션은 객체가 생성되는 가과정을 전혀 모른다는 뜻이다. 단지 모든 객체가 적절히 생성되었다고 가정한다.

![image](https://user-images.githubusercontent.com/39984656/106633956-a585c400-65c2-11eb-8689-b5c47b430ddf.png)

### 팩토리
객체가 생성되는 시점을 애플리케이션이 결정할 필요도 생긴다.
이때는 Abstract Factory 패턴을 사용한다.
![image](https://user-images.githubusercontent.com/39984656/106634148-d6fe8f80-65c2-11eb-94b6-6578b2f02c2f.png)
`OrderProcessing`은 `LineItem`이 생성되는 구체적인 방법을 모른다
그 방법은 `LineItemFactoryImplementation`이 안다.
그럼에도 `OrderProcessing`은 `LineItem` 인스턴스가 생성되는 시점을 완벽하게 통제하며
필요하다면 `OrderProcessing` 애플리케이션에서만 사용하는 생성자 인수도 넘길 수 있다.

### 의존성 주입
사용과 제작을 분리하는 강력한 메커니즘 하나가 의존성주입<sup>Dependency Injection</sup>이다.
의존성 주입은 제어 역전<sup>Inversion of Control</sup>기법을 의존성 관리에 적용한 매커니즘이다.

한 객체가 맡은 보조 책임을 새로운 객체에게 전적으로 떠넘긴다.
새로운 객체는 넘겨받은 책임만 맡으므로 단일책임원칙<sup>Single Responsibility Principle, SRP</sup>을 지키게 된다.

```
MyService myService = applicationContext.getBean(MyService.class)
```

## 확장
군락은 마을로 마을은 도시로 성장한다.
점차 넓어지는 것이다. 코드도 동일하다. 

테스트 주도 개발<sup>Test Driven Development, TDD</sup>, 리펙터링 으로 얻어지는
깨끗한 코드는 코드 수준에서 시스템을 조정하고 확장하기 쉽게 만들어 준다.

>소프트웨어 시스템은 물리적인 시스템과 다르다.
>관심사를 적절히 분리해 관리한다면 소프트웨어 아키텍처는 점진적으로 발전할 수 있다

### 횡단(cross-cutting) 관심사
EJB(Enterprise JavaBeans)은 일부 영역에서 관심사를 완벽하게 분리한다.
예를들어 원하는 트랜잭션, 보안, 일부 영속적인 동작은 소스코드가 아니라 배치 기술자가 정의한다.

영속성과 같은 **관심사**는 애플리케이션의 자연스러운 경계를 넘나드는 경향이 있다.
모든 객체가 전반적으로 동일한 방식을 이용하게 만들어야한다.

원론적으로는 모듈화되고 캡슐화된 방식으로 영속성 방식을 구상할 수 있다.
하지만 현실적으로는 영속성 방식을 구현한 코드가 온갖 객체로 흩어진다.
여기서  **횡단 관심사**라는 용어가 나온다,

AOP<sup>Aspect Oriented Programming</sup>는 횡단 관심사에 대처해 모듈성을 확보하는 일반적인 방법론이다.
AOP에서 관점(Aspect) 이라는 모듈 구성 개념은
특정 관심사를 지원하려면 시스템에서 특정 지점들이 동작하는 방식을 일관성 있게 바꿔야 한다.

## 자바 프록시
자바 프록시는 단순한 상황에 적합하다.
개별 객체나 클래스에서 메서드 호출을 감싸는 경우가 좋은 예다.
```
// Bank.java ((패키지 이름을 감춘다)
import java.utils.*;

// The abstraction of a bank.
public interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
}

// BankImpl.java 
import java.utils.*;

// 추상화를 위한 POJO("Plain Old Java Object") 구현
public class BankImpl implements Bank {
    private List<Account> accounts;

    public Collection<Account> getAccounts() {
        return accounts;
    }
    
    public void setAccounts(Collection<Account> accounts) {
        this.accounts = new ArrayList<Account>();
        for (Account account: accounts) {
            this.accounts.add(account);
        }
    }
}
// BankProxyHandler.java
import java.lang.reflect.*;
import java.util.*;

// 프록시 API가 필요한 “InvocationHandler”
public class BankProxyHandler implements InvocationHandler {
    private Bank bank;
    
    public BankHandler (Bank bank) {
        this.bank = bank;
    }
    
    // InvocationHandler에 정의된 메서드
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.equals("getAccounts")) {
            bank.setAccounts(getAccountsFromDatabase());
            
            return bank.getAccounts();
        } else if (methodName.equals("setAccounts")) {
            bank.setAccounts((Collection<Account>) args[0]);
            setAccountsToDatabase(bank.getAccounts());
            
            return null;
        } else {
            ...
        }
    }
    
    // 세부사항은 여기에 이어진다.
    protected Collection<Account> getAccountsFromDatabase() { ... }
    protected void setAccountsToDatabase(Collection<Account> accounts) { ... }
}

// 다른곳에 위치하는 코드
Bank bank = (Bank) Proxy.newProxyInstance(
    Bank.class.getClassLoader(),
    new Class[] { Bank.class },
    new BankProxyHandler(new BankImpl())
);
```

## 순수 자바 AOP 프레임워크
AOP Framework (Spring AOP, JBoss AOP)를 통해서 위의 단점들을 해결할 수 있다. Spring에서는 비지니스 로직을 POJO로 작성해 해당 도메인에만 초점을 맞추면 된다. 따라서 테스트가 개념적으로 더 쉽고 간단하다.

```
beans>
    ...
    <bean id="appDataSource"
        class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close"
        p:driverClassName="com.mysql.jdbc.Driver"
        p:url="jdbc:mysql://localhost:3306/mydb"
        p:username="me"/>
    
    <bean id="bankDataAccessObject"
        class="com.example.banking.persistence.BankDataAccessObject"
        p:dataSource-ref="appDataSource"/>
    
    <bean id="bank"
        class="com.example.banking.model.Bank"
        p:dataAccessObject-ref="bankDataAccessObject"/>
    ...
</beans>
```
```
XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("app.xml", getClass()));
Bank bank = (Bank) bf.getBean("bank");
```
![image](https://user-images.githubusercontent.com/39984656/106636663-80468500-65c5-11eb-8713-97ef4c3f1277.png)

라이언트에서 Bank의 getAccount()를 호출한다고 믿지만 실제로는 DECORATOR 객체 집합의 가장 외곽과 통신한다. xml이라 읽기 힘들 수는 있지만, Java Proxy보다는 간결하다.

EBJ3을 이용하여 다시 작성한 코드이다.
```
package com.example.banking.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "BANKS")
public class Bank implements java.io.Serializable {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Embeddable // An object “inlined” in Bank’s DB row
    public class Address {
        protected String streetAddr1;
        protected String streetAddr2;
        protected String city;
        protected String state;
        protected String zipCode;
    }
    
    @Embedded
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="bank")
    private Collection<Account> accounts = new ArrayList<Account>();
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void addAccount(Account account) {
        account.setBank(this);
        accounts.add(account);
    }
    
    public Collection<Account> getAccounts() {
        return accounts;
    }
    
    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
```


## AssertJ 관점
AspectJ는 AOP를 실현하기 위한 full-featured tool이라 일컬어진다. 8~90%의 경우에는 Spring AOP와 JBoss AOP로도 충분하지만 AspectJ는 훨씬 강력한 수준의 AOP를 지원한다. 다만 이를 사용하기 위해 새로운 툴, 언어 구조, 관습적인 코드를 익혀야 한다는 단점도 존재한다.(최근 소개된 "annotation-form AspectJ"로 인해 적용에 필요한 노력은 많이 줄어들었다고 한다.)


## 테스트 주도 시스템 아키텍쳐 구축
코드 레벨에서부터 아키텍쳐와 분리된(decouple된) 프로그램 작성은 당신의 아키텍쳐를 test drive하기 쉽게 만들어 준다. 


## 의사 결정을 최적화 하라
충분히 큰 시스템에서는(그것이 도시이건 소프트웨어이건) 한 사람이 모든 결정을 내릴 수는 없다. 
결정은 최대한 많은 정보가 모일 때까지 미루고 시기가 되었을 경우 해당 파트의 책임자(여기서는 사람이 아닌 모듈화된 컴포넌트를 뜻한다)에게 맡기는 것이 
불필요한 고객 피드백과 고통을 덜어줄 것이다.

>모듈화된 관심 분야로 이루어진 POJO 시스템의 (변화에 대한)민첩함은 가장 최신의 정보를 가지고 적시에 최적의 선택을 할 수 있게 도와준다. 
>결정에 필요한 복잡도 또한 경감된다.


## 명백한 가치가 있을 때 표준을 현명하게 사용하라
많은 소프트웨어 팀들은 훨씬 가볍고 직관적인 디자인이 가능했음에도 불구하고 그저 표준이라는 이유만으로 EJB2 구조를 사용했다. 
표준에 심취해 "고객을 위한 가치 창출"이라는 목표를 잃어 버렸기 때문이다.


## 시스템 도메인 특화 언어가 필요하다.
좋은 DSL은 도메인 영역의 개념과 실제 구현될 코드 사이의 "소통의 간극"을 줄여 도메인 영역을 코드 구현으로 번역하는 데에 오역을 줄여준다. 
DSL을 효율적으로 사용하면 코드 덩어리와 디자인 패턴의 추상도를 높여 주며 그에 따라 코드의 의도를 적절한 추상화 레벨에서 표현할 수 있게 해준다.