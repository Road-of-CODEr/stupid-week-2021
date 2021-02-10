# Chapter 10 - 클래스

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 10 - 클래스](#chapter-10-클래스)
  - [클래스 체계](#클래스-체계)
    - [캡슐화](#캡슐화)
  - [클래스는 작아야 한다!](#클래스는-작아야-한다)
    - [단일 책임 원칙](#단일-책임-원칙)
    - [응집도Cohesion](#응집도supcohesionsup)
    - [응집도를 유지하면 작은 클래스 여럿이 나온다.](#응집도를-유지하면-작은-클래스-여럿이-나온다)
  - [변경하기 쉬운 클래스](#변경하기-쉬운-클래스)
    - [변경으로부터 격리](#변경으로부터-격리)

<!-- /code_chunk_output -->

## 클래스 체계  
자바의 클레스 정의 관례
1. static public 상수가 있다면 맨 처음 선언
2. 가장 먼저 변수 목록 선언
3. 비공개 인스턴스 변수 선언
4. 공개함수 선언
5. 비공개 함수를 호출하려는 함수 직후에 선언

### 캡슐화
변수와 유틸리티 함수는 가능한 공개하지 않는 편이 낫지만 반드시 숨겨야 한 다는 법칙도 없다.
때때로 `protected`로 선언하여 테스트코드에 접근을 허용하기도 한다.


## 클래스는 작아야 한다!
- 클래스는 첫 번째로 작아야한다. 두 번째로 더 작아야 한다.
- 클래스 이름은 해당 클래스의 책임을 기술해야 한다.
    - 간결한 이름이 떠오르지 않는다면 그 클래스는 크기가 큰 것이다.
- 클래스이름이 Processor, Manager, Super 등과 같이 모호한 단어가 있다면 해당 클래스에 여러 책임을 떠안겼다는 증거이다.
- 클래스의 설명은 만일("if"), 그리고("and"), -(하)며("or"), 하지만("but")을 사용하지 않고 25단어 내외로 가능해야 한다.

### 단일 책임 원칙
단일 책임 원칙<sup>Single Responsibility Principle, SRP</sup>은 클래스나 모듈을 변경할 이유가 하나, 단 하나뿐이여야 한다는 원칙이다.

```
public class Version {
  public int getMajorVersionNumber();
  public int getMinorVersionNumber();
  public int getBuildNumber();
}
```

책임, 즉 변경할 이유를 파악하려 애 쓰다 보면 코드를 추상화 하기 쉬워진다. 더 좋은 추상화가 쉽게 떠오른다.

많은 개발자들은 단일 책임 클래스가 많아지면 큰 그림을 이해하기 어려워진다고 우려한다.
큰 그림을 이해하려면 이 클래스 저 클래스를 수없이 넘나들어야 한다고 걱정한다.
하지만 작은 클래스가 많은 시스템이든 큰 클래스가 몇 개 뿐인 시스템이든 들어가는 부품수는 비슷하다.
> 도구상자를 어떻게 관리하고 싶은가? 
> 작은 서랍을 두고 기능과 이름이 명확한 컴포넌트를 나눠 넣고 싶은가 ?
> 아니면 큰 서랍 몇 개를 두고 모두 던져 넣고 싶은가?

큰 클래스 몇 개가 아니라, 작은 클래스 여럿으로 이뤄진 시스템이 더 바람직하다.
작은 클래스는 각자 맡은 책임이 하나이며, 변경할 이유가 하나이며, 작은 클래스와 협력해 시스템에 필요한 동작을 수행한다.

### 응집도<sup>Cohesion</sup>
클래스는 인스턴스 변수 수가 작아야 한다.
각 클래스 메서드는 클래스 인스턴수 변수를 하나 이상 사용해야 한다.
일반적으로 메서드가 변수를 더 많이 사용할 수록 메서드와 클래스는 응집도가 더 높다.
모든 인스턴스 변수를 메서드마다 사용하는 클래스는 응집도가 가장 높다.

```
// 응집도가 아주 높은 Stack클래스
// size()를 제외한 다른 두 메소드는 모두 인스턴스 변수를 사용한다.
public class Stack {
  private int topOfStack = 0;
  List<Integer> elements = new LinkedList<>();
  
  public int size() {
    return topOfStack;
  }
  
  public void push(int element) {
    topOfStack++;
    elements.add(element);
  }
  
  public int pop() throws PoppedWhenEmpty {
    if (topOfStack == 0) {
      throw new PoppedWhenEmpty();
    }
    
    int element = elements.get(--topOfStack);
    elements.remove(topOfStack);
    return element;
  }
}
```

**함수를 작게, 매개변수 목록을 짧게**라는 전략을 따르다보면 때때로 몇몇 메서드만 사용하는 인스턴스 변수가 많아진다. 
이는 새로운 클래스로 쪼개야 한다는 신호이다.

### 응집도를 유지하면 작은 클래스 여럿이 나온다.
큰 함수를 작은 함수로 여럿 나누기만해도 클래스 수가 많아진다.

1. 변수가 아주 많은 큰 함수가 하나 있다
2. 큰 함수 일부를 작은 함수 하나로 빼내고 싶은데, 빼내려는 코드가 큰 함수에 정의된 변수 넷을 사용한다
3. 네 변수를 클래스 인스턴스로 승격한다. 새 함수는 인수가 필요 없으므로 함수를 쪼개기 쉬워진다
4. 몇몇 함수만 사용하는 인스턴스 변수가 많아지면 클래스가 응집력을 잃는다.
5. 몇몇 함수가 몇몇 인스턴스 변수만 사용한다면 독자적인 클래스로 분리가 가능해진다

```
// 1. 함수가 하나 뿐인 엉망진창 프로그램
public class PrintPrimes {
  public static void main(String[] args) {
    final int M = 1000;
    final int RR = 50;
    final int CC = 4;
    final int WW = 10;
    final int ORDMAX = 30;
    int P[] = new int[M + 1];
    int PAGENUMBER;
    int PAGEOFFSET;
    int ROWOFFSET;
    int C;
    int J;
    int K;
    boolean JPRIME;
    int ORD;
    int SQUARE;
    int N;
    int MULT[] = new int[ORDMAX + 1];

    J = 1;
    K = 1;
    P[1] = 2;
    ORD = 2;
    SQUARE = 9;

    while (K < M) {
      do {
        J = J + 2;
        if (J == SQUARE) {
          ORD = ORD + 1;
          SQUARE = P[ORD] * P[ORD];
          MULT[ORD - 1] = J;
        }
        N = 2;
        JPRIME = true;
        while (N < ORD && JPRIME) {
          while (MULT[N] < J)
            MULT[N] = MULT[N] + P[N] + P[N];
          if (MULT[N] == J)
            JPRIME = false;
          N = N + 1;
        }
      } while (!JPRIME);
      K = K + 1;
      P[K] = J;
    }
    {
      PAGENUMBER = 1;
      PAGEOFFSET = 1;
      while (PAGEOFFSET <= M) {
        System.out.println("The First " + M +
                             " Prime Numbers --- Page " + PAGENUMBER);
        System.out.println("");
        for (ROWOFFSET = PAGEOFFSET; ROWOFFSET < PAGEOFFSET + RR; ROWOFFSET++){
          for (C = 0; C < CC;C++)
            if (ROWOFFSET + C * RR <= M)
              System.out.format("%10d", P[ROWOFFSET + C * RR]);
          System.out.println("");
        }
        System.out.println("\f");
        PAGENUMBER = PAGENUMBER + 1;
        PAGEOFFSET = PAGEOFFSET + RR * CC;
      }
    }
  }
}
```

```
// 2. 리펙토링, 함수와 클래스로 나눈 후 함수와 클래스 변수에 좀 더 의미있는 이름 부여
public class PrimePrinter {
  public static void main(String[] args) {
    final int NUMBER_OF_PRIMES = 1000;
    int[] primes = PrimeGenerator.generate(NUMBER_OF_PRIMES);

    final int ROWS_PER_PAGE = 50;
    final int COLUMNS_PER_PAGE = 4;
    RowColumnPagePrinter tablePrinter =
      new RowColumnPagePrinter(ROWS_PER_PAGE,
                               COLUMNS_PER_PAGE,
                               "The First " + NUMBER_OF_PRIMES +
                                 " Prime Numbers");

    tablePrinter.print(primes);
  }

}
```
```
// 3리펙토링, 함수와 클래스로 나눈 후 함수와 클래스 변수에 좀 더 의미있는 이름 부여
public class RowColumnPagePrinter {
  private int rowsPerPage;
  private int columnsPerPage;
  private int numbersPerPage;
  private String pageHeader;
  private PrintStream printStream;

  public RowColumnPagePrinter(int rowsPerPage,
                              int columnsPerPage,
                              String pageHeader) {
    this.rowsPerPage = rowsPerPage;
    this.columnsPerPage = columnsPerPage;
    this.pageHeader = pageHeader;
    numbersPerPage = rowsPerPage * columnsPerPage;
    printStream = System.out;
  }
  public void print(int data[]) {
    int pageNumber = 1;
    for (int firstIndexOnPage = 0;
         firstIndexOnPage < data.length;
         firstIndexOnPage += numbersPerPage) {
      int lastIndexOnPage =
        Math.min(firstIndexOnPage + numbersPerPage - 1,
                 data.length - 1);
      printPageHeader(pageHeader, pageNumber);
      printPage(firstIndexOnPage, lastIndexOnPage, data);
      printStream.println("\f");
      pageNumber++;
    }
  }

  private void printPage(int firstIndexOnPage,
                         int lastIndexOnPage,
                         int[] data) {
    int firstIndexOfLastRowOnPage =
      firstIndexOnPage + rowsPerPage - 1;
    for (int firstIndexInRow = firstIndexOnPage;
         firstIndexInRow <= firstIndexOfLastRowOnPage;
         firstIndexInRow++) {
      printRow(firstIndexInRow, lastIndexOnPage, data);
      printStream.println("");
    }
  }

  private void printRow(int firstIndexInRow,
                        int lastIndexOnPage,
                        int[] data) {
    for (int column = 0; column < columnsPerPage; column++) {
      int index = firstIndexInRow + column * rowsPerPage;
      if (index <= lastIndexOnPage)
        printStream.format("%10d", data[index]);
    }
  }

  private void printPageHeader(String pageHeader,
                               int pageNumber) {
    printStream.println(pageHeader + " --- Page " + pageNumber);
    printStream.println("");
  }

  public void setOutput(PrintStream printStream) {
    this.printStream = printStream;
  }
}
```
```
// 4.리펙토링, 함수와 클래스로 나눈 후 함수와 클래스 변수에 좀 더 의미있는 이름 부여    `
public class PrimeGenerator {
  private static int[] primes;
  private static ArrayList<Integer> multiplesOfPrimeFactors;

  protected static int[] generate(int n) {
    primes = new int[n];
    multiplesOfPrimeFactors = new ArrayList<Integer>();
    set2AsFirstPrime();
    checkOddNumbersForSubsequentPrimes();
    return primes;
  }

  private static void set2AsFirstPrime() {
    primes[0] = 2;
    multiplesOfPrimeFactors.add(2);
  }

  private static void checkOddNumbersForSubsequentPrimes() {
    int primeIndex = 1;
    for (int candidate = 3;
         primeIndex < primes.length;
         candidate += 2) {
      if (isPrime(candidate))
        primes[primeIndex++] = candidate;
    }
  }

  private static boolean isPrime(int candidate) {
    if (isLeastRelevantMultipleOfNextLargerPrimeFactor(candidate)) {
      multiplesOfPrimeFactors.add(candidate);
      return false;
    }
    return isNotMultipleOfAnyPreviousPrimeFactor(candidate);
  }

  private static boolean
  isLeastRelevantMultipleOfNextLargerPrimeFactor(int candidate) {
    int nextLargerPrimeFactor = primes[multiplesOfPrimeFactors.size()];
    int leastRelevantMultiple = nextLargerPrimeFactor * nextLargerPrimeFactor;
    return candidate == leastRelevantMultiple;
  }

  private static boolean
  isNotMultipleOfAnyPreviousPrimeFactor(int candidate) {
    for (int n = 1; n < multiplesOfPrimeFactors.size(); n++) {
      if (isMultipleOfNthPrimeFactor(candidate, n))
        return false;
      }
    return true;
  }

  private static boolean
  isMultipleOfNthPrimeFactor(int candidate, int n) {
    return
      candidate == smallestOddNthMultipleNotLessThanCandidate(candidate, n);
  }

  private static int
  smallestOddNthMultipleNotLessThanCandidate(int candidate, int n) {
    int multiple = multiplesOfPrimeFactors.get(n);
    while (multiple < candidate)
      multiple += 2 * primes[n];
    multiplesOfPrimeFactors.set(n, multiple);
    return multiple;
  }
}
```
리펙토링 후에 프로그램 길이가 길어졌다 이유는
- 좀 더 길고 서술적인 변수 이름 사용
- 코드에 주석을 추가하는 수단으로 함수 선언과 클래스 선언을 사용
- 가독성을 높이고자 공백을 추가하고 형식을 맞춤

리펙토링후 프로그램은 세가지 책임으로 나누어 졌다.
1. `PrimPrinter` 클래스는 main함수만 포함하여 실행환경을 책임진다
2. `RowColumnPagePrinter` 클래스는 숫자 목록을 주어진 행과 열에 맞춰 페이지에 출력하는 방법을 책임진다
3. `PrimeGenerator` 클래스는 소수 목록을 생성하는 방법을 책임진다


## 변경하기 쉬운 클래스
대다수의 시스템은 지속적인 변경이 가해진다.
그리고 뭔가 변경할 때마다 시스템이 의도대로 동작하지 않을 위험이 따른다.
깨끗한 시스템은 클래스를 체계적으로 정리해 변경에 수반하는 위험을 낮춘다.

```
// Bad
// 변경이 필요할 때마다 손대야 하는 클래스
public class Sql {
  public Sql(String table, Column[] columns);
  public String create();
  public String insert(Object[] fields);
  public String selectAll();
  public String findByKey(String keyColumn, String keyValue);
  public String select(Column column, String pattern);
  public String select(Criteria criteria);
  public String preparedInsert();
  private String columnList(Column[] columns);
  private String valuesList(Object[] fields, final Column[] columns);
  private String selectWithCriteria(String criteria);
  private String placeholderList(Column[] columns);
}
```
위 클래스는 새로운 SQL문을 지원하려면 반드시 Sql클래스에 손대야 한다.
기존 SQL문 하나를 수정할 때도 반드시 Sql 클래스에 손대야 한다.
SRP와 OCP를 위반한다.

```
// Good
abstract public class Sql {
   public Sql(String table, Column[] columns) {};
   abstract public String generate();
}

class CreateSql extends Sql {
   public CreateSql(String table, Column[] columns) { super(table, columns); }
   @Override public String generate() { return ""; }
}

class SelectSql extends Sql {
   public SelectSql(String table, Column[] columns) { super(table, columns); }
   @Override public String generate() { return ""; }
}

class InsertSql extends Sql {
   public InsertSql(String table, Column[] columns, Object[] fields) { super(table, columns); }
   @Override public String generate() { return ""; }
   private String valuesList(Object[] fields, final Column[] columns) { return ""; }
}

class SelectWithCriteriaSql extends Sql {
   public SelectWithCriteriaSql(
      String table, Column[] columns, Criteria criteria) { super(table, columns); }
   @Override public String generate() { return ""; }
}

class SelectWithMatchSql extends Sql {
   public SelectWithMatchSql(
      String table, Column[] columns, Column column, String pattern) { super(table, columns); }
   @Override public String generate() { return ""; }
}

class FindByKeySql extends Sql {
   public FindByKeySql(
      String table, Column[] columns, String keyColumn, String keyValue) { super(table, columns); }
   @Override public String generate() { return ""; }
}

class PreparedInsertSql extends Sql {
   public PreparedInsertSql(String table, Column[] columns) { super(table, columns); }
   @Override public String generate() {return ""; }
   private String placeholderList(Column[] columns) { return ""; }
}

class Where {
   public Where(String criteria) {}
   public String generate() { return ""; }
}

class ColumnList {
   public ColumnList(Column[] columns) {}
   public String generate() { return ""; }
}
```
클래스의 책임을 분리하였을때 
- 코드가 이해하 쉬워진다
- 함수 하나를 수정했다고 다른 함수가 망가질 위험이 사라졌다
- 테스트 관점에서 모든 논리를 구석구석 증명하기 쉬워졌다

위 클래스는 SRP와 OCP<sup>Open Close Principle</sup>을 잘 지킨다.
> OCP : 확장에 개방적이고 수정에 폐쇄적이여야 한다는 원칙.

### 변경으로부터 격리
상세한 구현<sup>concrete</sup>에 의존하는 클래스는 구현이 변경되면 위험에 빠진다.
또한 상세한 구현에 의존하는 코드는 테스트가 어렵다


```
interface StockExchange {
  Money currentPrice(String symbol);
}
```
 `StockExchange` 인터페이스를 구현하는 `TokyoStockExchange` 클래스를 구현한다고 하자.
```
public Portfolio {
  private StockExchange stockExchange;
  
  public Portfolio(StockExchange stockExchange) {
    this.stockExchange = stockExchange;
  }
  // ...
}
```
`Portfolio` 클래스를 생성할때 `TokyoStockExchange`가 아닌 `StockExchange`클래스를 흉내내는 테스트용 클래스(Mock)를 만들 수 있다.
```
public class PortfolioTest {
  private StockExchange exchange;
  private Portfolio portfolio;
  
  @Before
  protected void setUp() throws Exception {
    exchange = new FixedStockExchangeStub();
    exchange.fix("MSFT", 100);
    portfolio = new Portfolio(exchange);
  }
  
  @Test
  public void GivenFiveMSFTTotalShouldBe500() throws Exception {
    portfolio.add(5, "MSFT");
    assertThat(portfolio.value()).isEqualTo(500)
  }
}
```

위처럼 테스트가 가능할 정도로 시스템의 결합도를 낮추면 유연성과 재사용성도 더욱 높아진다.
결합도를 줄이게 되면 자연스럽게 클래스 설계 원칙인 DIP<sup>Dependency Inversion Principle</sup>을 지키는 클래스가 나온다.
> DIP: 본질적으로 클래스는 상세한 구현이 아니라 추상화에 의존해야 한다는 원칙z