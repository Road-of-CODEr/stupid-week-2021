# Chapter 9 - 단위 테스트

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 9 - 단위 테스트](#chapter-9-단위-테스트)
  - [TDD 법칙 세가지](#tdd-법칙-세가지)
  - [깨끗한 테스트 코드 유지하기](#깨끗한-테스트-코드-유지하기)
    - [테스트는 유연성, 유지보수성, 재사용성을 제공한다.](#테스트는-유연성-유지보수성-재사용성을-제공한다)
  - [깨끗한 테스트 코드](#깨끗한-테스트-코드)
    - [도메인에 특화된 테스트 언어](#도메인에-특화된-테스트-언어)
    - [이중 표준](#이중-표준)
  - [테스트 당 assert 하나](#테스트-당-assert-하나)
  - [F.I.R.S.T](#first)
  - [결론](#결론)

<!-- /code_chunk_output -->


## TDD 법칙 세가지
1. 실패하는 단위 테스트를 작성할 때 까지 코드를 작성하지 않는다.
2. 컴파일은 실패하지 않으면서 실행이 실패하는 정도만 단위테스트를 작성한다.
3. 현재 실패하는 테스트를 통과할 정도로만 실제 코드를 작성한다.

위 세가지 규칙을 따르면 개발과 테스트가 대략 30초 주기로 묶인다.
테스트 코드와 실제 코드가 함께 나올 뿐더러 테스트 코드가 실제 코드보다 불과 몇 초전에 나온다.

위 처럼 일하면 매년 수천개에 달하는 테스트 케이스가 나온다.
하지만 실제 코드와 맞먹을 정도로 방대한 테스트 코드는 심각한 관리 문제를 유발하기도 한다.


## 깨끗한 테스트 코드 유지하기
테스트 코드는 실제 코드 못지않게 중요하다.
실제 코드가 변경되면 테스트코드도 변경되어야 하므로, 테스트코드가 복잡할 수록 
지저분한 코드로 인해 실패하는 테스트 케이스를 통과시키기 더욱 어려워진다.
지저분한 테스트 코드는 테스트를 안하는 것 보다 못한다.
테스트 코드도 실제 코드 못지않게 짜야한다.

### 테스트는 유연성, 유지보수성, 재사용성을 제공한다.
테스트코드를 깨끗하게 유지하지 않으면 결국 잃어버린다. 
그리고 테스트 케이스가 없으면 실제 코드를 유연하게 만드는 버팀목도 사라진다.
**코드에 유연성, 유지보수성, 재용성을 제공하는 버팀목이 바로 단위테스트 이다.**


## 깨끗한 테스트 코드
깨끗한 테스트 코드는 세 가지가 필요하다. **가독성, 가독성, 가독성**
```
// Bad
public void testGetPageHieratchyAsXml() throws Exception {
  crawler.addPage(root, PathParser.parse("PageOne"));
  crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
  crawler.addPage(root, PathParser.parse("PageTwo"));

  request.setResource("root");
  request.addInput("type", "pages");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("<name>PageOne</name>", xml);
  assertSubString("<name>PageTwo</name>", xml);
  assertSubString("<name>ChildOne</name>", xml);
}

public void testGetPageHieratchyAsXmlDoesntContainSymbolicLinks() throws Exception {
  WikiPage pageOne = crawler.addPage(root, PathParser.parse("PageOne"));
  crawler.addPage(root, PathParser.parse("PageOne.ChildOne"));
  crawler.addPage(root, PathParser.parse("PageTwo"));

  PageData data = pageOne.getData();
  WikiPageProperties properties = data.getProperties();
  WikiPageProperty symLinks = properties.set(SymbolicPage.PROPERTY_NAME);
  symLinks.set("SymPage", "PageTwo");
  pageOne.commit(data);

  request.setResource("root");
  request.addInput("type", "pages");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("<name>PageOne</name>", xml);
  assertSubString("<name>PageTwo</name>", xml);
  assertSubString("<name>ChildOne</name>", xml);
  assertNotSubString("SymPage", xml);
}

public void testGetDataAsHtml() throws Exception {
  crawler.addPage(root, PathParser.parse("TestPageOne"), "test page");

  request.setResource("TestPageOne"); request.addInput("type", "data");
  Responder responder = new SerializedPageResponder();
  SimpleResponse response =
    (SimpleResponse) responder.makeResponse(new FitNesseContext(root), request);
  String xml = response.getContent();

  assertEquals("text/xml", response.getContentType());
  assertSubString("test page", xml);
  assertSubString("<Test", xml);
}
```
예를 들어, PathParser 호출을 살펴보자. PathParser는 문자열을 pagePath 인스턴스로 변환한다. 이 코드는 테스트와 무관하며 테스트 코드의 의도만 흐린다. responder 객체를 생성하는 코드와 response를 수집해 변환하는 코드 역시 잡음에 불과하다. 게다가 resource와 인수에서 요청 URL을 만드는 어설픈 코드도 보인다.
그리고 읽는 사람을 고려하지 않는다

```
// Good
public void testGetPageHierarchyAsXml() throws Exception {
  makePages("PageOne", "PageOne.ChildOne", "PageTwo");

  submitRequest("root", "type:pages");

  assertResponseIsXML();
  assertResponseContains(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>");
}

public void testSymbolicLinksAreNotInXmlPageHierarchy() throws Exception {
  WikiPage page = makePage("PageOne");
  makePages("PageOne.ChildOne", "PageTwo");

  addLinkTo(page, "PageTwo", "SymPage");

  submitRequest("root", "type:pages");

  assertResponseIsXML();
  assertResponseContains(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>");
  assertResponseDoesNotContain("SymPage");
}

public void testGetDataAsXml() throws Exception {
  makePageWithContent("TestPageOne", "test page");

  submitRequest("TestPageOne", "type:data");

  assertResponseIsXML();
  assertResponseContains("test page", "<Test");
}
```
BUILD-OPERATE-CHECK 패턴이 위와 같은 테스트 구조에 적합하다. 
각 테스트는 명확히 세 부분으로 나눠진다.
1. 테스트 자료를 만든다
2. 테스트 자료를 조작한다.
3. 조작한 결과가 올바른지 확인한다.

### 도메인에 특화된 테스트 언어
바로 위의 코드는 도메인에 특화된언어<sup>DSL</sup>으로 테스트 코드를 구현하는 기법을 보여준다.
시스템 조작 API를 사용하는 대신 API위에다 함수와 유틸리티를 구현한 후 그 함수와 유틸리티를 사용하므로 테스트 코드를 짜기도 읽기도 쉬워진다

### 이중 표준
테스트 적용하는 표준은 실제 코드에 적용하는 표준과 확실히 다르다. 
단순하고, 간결하고, 표현력이 풍부해야 하지만, 실제 코드만큼 효율적일 필요는 없다.
```
// Bad
@Test
public void turnOnLoTempAlarmAtThreashold() throws Exception {
  hw.setTemp(WAY_TOO_COLD); 
  controller.tic(); 
  assertTrue(hw.heaterState());   
  assertTrue(hw.blowerState()); 
  assertFalse(hw.coolerState()); 
  assertFalse(hw.hiTempAlarm());       
  assertTrue(hw.loTempAlarm());
}
```

```
// Good
@Test
public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
  tooHot();
  assertEquals("hBChl", hw.getState()); 
}
  
@Test
public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
  tooCold();
  assertEquals("HBchl", hw.getState()); 
}

@Test
public void turnOnHiTempAlarmAtThreshold() throws Exception {
  wayTooHot();
  assertEquals("hBCHl", hw.getState()); 
}

@Test
public void turnOnLoTempAlarmAtThreshold() throws Exception {
  wayTooCold();
  assertEquals("HBchL", hw.getState()); 
}
```

```
public String getState() {
  String state = "";
  state += heater ? "H" : "h"; 
  state += blower ? "B" : "b"; 
  state += cooler ? "C" : "c"; 
  state += hiTempAlarm ? "H" : "h"; 
  state += loTempAlarm ? "L" : "l"; 
  return state;
}
```
getState함수는 효츌적이지 못하자
효율을 높이려면 StringBuffer가 더 적합하다.
하지만 테스트 환경이므로 자원이 제한적일 가능성이 낮다.

이것이 이중표준의 본질이다.
실제환경에서는 절대로 안되지만 테스트 환경에서는 문제없는 방식이다.


## 테스트 당 assert 하나
JUnit으로 테스트 코드를 짤 때 함수마다 assert를 단 하나만 사용해야 한다고 주장하는 학파가 있다.
가혹하다 여길지 모르지만 확실히 장점이 있다. 
assert가 하나라면 결론이 하나기 때문에 코드를 이해하기 빠르고 쉽다.
```
public void testGetPageHierarchyAsXml() throws Exception { 
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  
  whenRequestIsIssued("root", "type:pages");
  
  thenResponseShouldBeXML(); 
}

public void testGetPageHierarchyHasRightTags() throws Exception { 
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  
  whenRequestIsIssued("root", "type:pages");
  
  thenResponseShouldContain(
    "<name>PageOne</name>", "<name>PageTwo</name>", "<name>ChildOne</name>"
  ); 
}
```
 "출력이 XML"이다 라는 assert 문과 "특정 문자열을 포함한다"는 assert문을 하나로 병합하는 방식이 불합리하다. 
 이 경우 테스트를 두 개로 쪼개 각자가 assert를 수행하면 된다.

 위에서 함수 이름을 바꿔 given-when-then 이라는 관례를 사용했다는 사실에 주목한다. 
 그러면 테스트 코드를 읽기가 쉬워진다. 하지만 불행하게도 위에서 보듯이 테스트를 분리하면 중복되는 코드가 많아진다.

 ### 테스트 당 개념 하나
어쩌면 "테스트 함수마다 한 개념만 테스트하라"는 규칙이 더 낫다
이것저것 잡다한 개념을 연속으로 테스트하는 긴 함수는 피한다.
```
/**
 * addMonth() 메서드를 테스트하는 장황한 코드
 */
public void testAddMonths() {
  SerialDate d1 = SerialDate.createInstance(31, 5, 2004);

  SerialDate d2 = SerialDate.addMonths(1, d1); 
  assertEquals(30, d2.getDayOfMonth()); 
  assertEquals(6, d2.getMonth()); 
  assertEquals(2004, d2.getYYYY());
  
  SerialDate d3 = SerialDate.addMonths(2, d1); 
  assertEquals(31, d3.getDayOfMonth()); 
  assertEquals(7, d3.getMonth()); 
  assertEquals(2004, d3.getYYYY());
  
  SerialDate d4 = SerialDate.addMonths(1, SerialDate.addMonths(1, d1)); 
  assertEquals(30, d4.getDayOfMonth());
  assertEquals(7, d4.getMonth());
  assertEquals(2004, d4.getYYYY());
}
```
독자적인 개념 세 개를 테스트하므로 독자적인 테스트 세 개로 쪼개야 마땅하다. 

가장 좋은 규칙은 "개념 당 assert 문 수를 최소로 줄여라"와 "테스트 함수 하나는 개념 하나만 테스트하라"라 하겠다.


## F.I.R.S.T
깨끗한 테스트는 다섯 가지 규칙을 따른다.
1. **F**irst
    - 테스트는 빨라야한다
    - 테스트가 느리다면 자주 돌릴 엄두를 못낸다
    - 자주 돌리지 않으면 초반에 문제를 찾아 고치지 못한다
2. **I**ndependent
    - 각 테스트는 서로 의존하면 안된다
    - 한 테스트가 다음 테스트가 실행될 환경을 준비해선 안된다
    - 독립적으로, 어떤 순서로도 실행해도 괜찮아야한다
    - 서로 의존하면 하나가 실패할경우, 연달아 실패하게 되므로 원인을 찾기 어렵다
3. **R**epeatable
    - 테스트는 어떤 환경에서도 반복 가능해야한다
    - 실제환경, QA환경, 버스를타고 집에가는 노트북환경(네트워크 x)에서도 실행가능해야한다
    - 테스트가 돌아가지 않는 환경이 있다면 테스트가 실패한 이유를 둘러댈 변명이 생긴다
4. **S**elf-Validating
    - 테스트는 bool값으로 결과를 나타내야한다
    - 성공 아니면 실패다
    - 통과여부를 알리고 로그파일을 읽게 해서는 안된다
    - 테스트가 스스로 성공과 실패를 가늠하지 않는다면 주관적이며 지루한 수작업 평가가 필요하게된다
5. **T**imely
    - 테스트는 적시에 작성해야한다
    - 단위 테스트는 테스트 하려는 실제 코드를 구현하기 직전에 구현한다
    - 실제 코드를 구현한 다음에 테스트 코드를 만들면 실제코드가 테스트하기 어렵다는 사실을 발견할 수 있다
    - 테스트가 불가능 하도록 실제 코드를 먼저 설계해버릴수도 있다


## 결론
테스트 코드는 실제 코드만큼이나 프로젝트 건강에 중요하다. 어쩌면 실제 코드보다 더 중요할지도 모르겠다.
테스트 코드는 실제 코드의 유연성, 유지보수성, 재사용성을 보존하고 강화하기 때문이다. 그러므로 테스트 코드는 지속적으로 깨끗하게 정리하자.
테스트 코드가 방치되어 망가지면 실제 코드도 망가진다. 테스트 코드를 깨끗하게 유지하자.