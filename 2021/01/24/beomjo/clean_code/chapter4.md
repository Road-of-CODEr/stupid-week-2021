# Chapter 4 - 주석

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 4 - 주석](#chapter-4-주석)
  - [주석은 나쁜 코드를 보완하지 못한다.](#주석은-나쁜-코드를-보완하지-못한다)
  - [코드로 의도를 표현하라](#코드로-의도를-표현하라)
  - [좋은 주석](#좋은-주석)
    - [법적인 주석](#법적인-주석)
    - [정보를 제공하는 주석](#정보를-제공하는-주석)
    - [의도를 설명하는 주석](#의도를-설명하는-주석)
    - [의미를 명료하게 밝히는 주석](#의미를-명료하게-밝히는-주석)
    - [결과를 경고하는 주석](#결과를-경고하는-주석)
    - [TODO 주석](#todo-주석)
    - [공개 API에서 Javadocs](#공개-api에서-javadocs)
  - [나쁜 주석](#나쁜-주석)
    - [주절거리는 주석](#주절거리는-주석)
    - [같은 이야기를 중복하는 주석](#같은-이야기를-중복하는-주석)
    - [오해할 여지가 있는 주석](#오해할-여지가-있는-주석)
    - [의무적으로 다는 주석](#의무적으로-다는-주석)
    - [이력을 기록하는 주석](#이력을-기록하는-주석)
    - [있으나 마나 한 주석](#있으나-마나-한-주석)
    - [무서운 잡음](#무서운-잡음)
    - [함수나 변수로 표현할 수 있다면 주석을 달지 마라](#함수나-변수로-표현할-수-있다면-주석을-달지-마라)
    - [위치를 표시하는 주석](#위치를-표시하는-주석)
    - [닫는 괄호에 다는 주석](#닫는-괄호에-다는-주석)
    - [공로를 돌리거나 저자를 표시하는 주석](#공로를-돌리거나-저자를-표시하는-주석)
    - [주석으로 처리한 코드](#주석으로-처리한-코드)
    - [HTML 주석](#html-주석)
    - [전역 정보](#전역-정보)
    - [너무 많은 정보](#너무-많은-정보)
    - [모호한 관계](#모호한-관계)
    - [함수 헤더](#함수-헤더)
    - [비공개 코드에서 Javadocs](#비공개-코드에서-javadocs)

<!-- /code_chunk_output -->

## 주석은 나쁜 코드를 보완하지 못한다.
코드에 주석을 추가하는 가장 큰 이유는 코드 품질이 나빠서이다. 짧고, 명확하게 알아볼 수 있는 깔끔한 코드에는 주석이 필요하지 않다.
주석으로 설명하려 애쓰는 대신 그 난장판을 깨끗이 치우는 데 시간을 보내자.


## 코드로 의도를 표현하라
몇 초만 더 생각해보면 주석이아닌 대다수는 코드로 표현이 가능하다.
```
// Bad
// 직원에게 복지 혜택을 받을 자격이 있는지 검사한다. 
if ((emplotee.flags & HOURLY_FLAG) && (employee.age > 65)


//Good
if (employee.isEligibleForFullBenefits())
```


## 좋은 주석
- 어떤 주석은 필요하거나 유익하다.
- 하지만 정말로 좋은 코드는 주석을 달지 않을 방법을 찾아낸 코드이다

### 법적인 주석
```
// Copyright (C) 2003, 2004, 2005 by Object Montor, Inc. All right reserved.
// GNU General Public License
```

### 정보를 제공하는 주석
하지만 정보를 제공하는 주석도 이름을 잘 짓는다면 굳이 필요하지 않다.
```
// 테스트 중인 Responder 인스턴스를 반환
protected abstract Responder responderInstance();
```

### 의도를 설명하는 주석
```
// 스레드를 대량 생성하는 방법으로 어떻게든 경쟁 조건을 만들려 시도한다. 
for (int i = 0; i > 2500; i++) {
    WidgetBuilderThread widgetBuilderThread = 
        new WidgetBuilderThread(widgetBuilder, text, parent, failFlag);
    Thread thread = new Thread(widgetBuilderThread);
    thread.start();
}
```

### 의미를 명료하게 밝히는 주석
```
assertTrue(a.compareTo(a) == 0); // a == a
assertTrue(a.compareTo(b) != 0); // a != b
assertTrue(ab.compareTo(ab) == -0); // ab == ab
assertTrue(a.compareTo(b) == -1); // a < a
assertTrue(aa.compareTo(ab) == 1); // aa < ab
assertTrue(ba.compareTo(aa) == 1); // ba > aa
assertTrue(bb.compareTo(ba) == 1); // bb > ba
assertTrue(b.compareTo(a) == 1); // b > a
assertTrue(ab.compareTo(aa) == 1); // ab > aa
```

### 결과를 경고하는 주석

경고를 위해 사용하는 경우 Ignore 또는 주석으로 달아놓는것이 좋다
```
// 여유 시간이 충분하지 않다면 실행하지 마십시오.
public void _testWithReallyBigFile()
public static simpleDateFormat makeStandardHttpDateFormat() {
  // SimpleDateFormat 은 스레드에 안전하지 못하다.
  // 그러므로 각 인스턴스를 독립적으로 생성해야 한다.
  SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM  yyyy HH:mm:ss z");
  df.setTimeZone(TimeZone.getTimeZone("GMT"));
  return df;
}
```

### TODO 주석
`//TODO` 주석은 앞으로 할 일을 남겨두면 좋다
하지만 나쁜 코드를 남겨놓는 핑계가 되어선 안된다.
```
// TODO-MdM 현재 필요하지 않다.
// 체크아웃 모델을 도입하면 함수가 필요 없다.
protected VersionInfo makeVersion() throws Exception {
    return null;
}
```

### 공개 API에서 Javadocs
설명이 잘 공개된 API는 참으로 유용하고 만족스럽다.
Javadocs가 좋은 에다


## 나쁜 주석
대다수의 주석이 이 범주에 속한다. 엉성한 코드를 변명하거나 허술한 코드를 지탱하려 한다.

### 주절거리는 주석
```
public void loadProperties() {
  try {
    String propertiesPath = propertiesLocation + "/" + PROPERTIES_FILE;
    FileInputStream propertiesStream = new FileInputStream(propertiesPath);
    loadedProperties.load(propertiesStream);
  } catch (IOException e) {
    // 속성 파일이 없다면 기본값을 모두 메모리로 읽어 들였다는 의미다. 
  }
}
```

### 같은 이야기를 중복하는 주석
코드 내용을 한글로 번역한듯 설명하는 주석은 달지말자.
```
// this.closed가 true일 때 반환되는 유틸리티 메서드다.
// 타임아웃에 도달하면 예외를 던진다. 
public synchronized void waitForClose(final long timeoutMillis) throws Exception {
  if (!closed) {
    wait(timeoutMillis);
    if (!closed) {
      throw new Exception("MockResponseSender could not be closed");
    }
  }
}
```

### 오해할 여지가 있는 주석
this.closed 가 true 로 변하는 순간에 반환하지 않는다. 
wait 의 타임아웃을 무조건 기다렸다가 지연하여 반환하게 되므로
잘못된 주석으로 인해 오해할 수 있다.
```
// this.closed가 true일 때 반환되는 유틸리티 메서드다.
// 타임아웃에 도달하면 예외를 던진다. 
public synchronized void waitForClose(final long timeoutMillis) throws Exception {
  if (!closed) {
    wait(timeoutMillis);
    if (!closed) {
      throw new Exception("MockResponseSender could not be closed");
    }
  }
}
```

### 의무적으로 다는 주석
모든 함수에 Javadocs를 달거나 모든 변수에 주석을 달 필요는 없다.
```
/**
 *
 * @param title CD 제목
 * @param author CD 저자
 * @param tracks CD 트랙 숫자
 * @param durationInMinutes CD 길이(단위: 분)
 */
public void addCD(String title, String author, int tracks, int durationInMinutes) {
    CD cd = new CD();
    cd.title = title;
    cd.author = author;
    cd.tracks = tracks;
    cd.duration = durationInMinutes;
    cdList.add(cd);
}
```

### 이력을 기록하는 주석
버전 관리프로그램이 다 이력을 관리해준다.
```
* 변경 이력 (11-Oct-2001부터)
* ------------------------------------------------
* 11-Oct-2001 : 클래스를 다시 정리하고 새로운 패키징
* 05-Nov-2001: getDescription() 메소드 추가
* 이하 생략
```


### 있으나 마나 한 주석
```
/*
 * 기본 생성자
 */
protected AnnualDateRule() {
}

/*
 * 월 중 일자
 */
 private int dayOfMonth();
```

### 무서운 잡음
잘라넣기 붙여넣기 오류등의
주의를 기울이지 않으면 잘못된 주석이 생길 수 있다.
```
/** The name */
private String name;

/** The version */
private String version;

/** The licenceName. */
private String licenceName;

/** The version */
private String info;
```

### 함수나 변수로 표현할 수 있다면 주석을 달지 마라
```
// Bad
// 전역 목록 <smodule>에 속하는 모듈이 우리가 속한 하위 시스템에 의존하는가?
if (module.getDependSubsystems().contains(subSysMod.getSubSystem()))
주석을 제거해 아래와 같이 표현하자

// Good
ArrayList moduleDependencies = smodule.getDependSubSystems();
String ourSubSystem = subSysMod.getSubSystem();
if (moduleDependees.contains(ourSubSystem))
```

### 위치를 표시하는 주석
다른 사람이 잘못된 위치에 넣는다면 의미가 없는 주석이 된다.
```
// Actions ////////////////////////////////
```

### 닫는 괄호에 다는 주석
```
try {
   ...
} catch(){
   ...
} // catch
```

### 공로를 돌리거나 저자를 표시하는 주석
버전 관리 프로그램에서 저자도 알아서 표시한다.
```
/* 릭이 추가함 */
```

### 주석으로 처리한 코드
깃이 버전 및 히스토리를 관리하므로 
필요 하지 않는 코드는 주석처리 하지말고 지워라.
```
this.bytePos = writeBytes(pngIdBytes, 0);
//hdrPos = bytePos;
writeHeader();
writeResolution();
//dataPos = bytePos;
if (writeImageData()) {
    wirteEnd();
    this.pngBytes = resizeByteArray(this.pngBytes, this.maxPos);
} else {
    this.pngBytes = null;
}
return this.pngBytes;
```

### HTML 주석
소스 코드에서 HTML 주석은 혐오 그 자체다.

### 전역 정보
주석을 달아야 한다면 근처에 있는 코드만 기술하라. 

### 너무 많은 정보
디테일하게 적을 필요가 없다.

### 모호한 관계
주석과 주석이 설명하는 코드는 둘 사이 관계가 명백해야 한다.

### 함수 헤더
짧은 함수는 긴 설명이 필요 없다. 헤더처리를 주석으로 하지마라.

### 비공개 코드에서 Javadocs
공개 API 가 아니라면 쓸모가 없다.