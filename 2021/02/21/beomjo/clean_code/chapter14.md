# Chapter 14 - 점진적인 개선

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter 14 - 점진적인 개선](#chapter-14-점진적인-개선)
  - [Intro](#intro)
  - [Args 구현](#args-구현)
    - [어떻게 짯느냐고?](#어떻게-짯느냐고)
  - [Args: 1차 초안](#args-1차-초안)
    - [그래서 멈췄다](#그래서-멈췄다)
    - [점진적으로 개선하다](#점진적으로-개선하다)
  - [String 인수](#string-인수)
  - [최종 코드](#최종-코드)
  - [결론](#결론)

<!-- /code_chunk_output -->


## Intro
점진적인 개선을 보여주는 사례를 알아보자
먼저 출발은 좋았으나 확장성이 부족했던 모듈을 소개한다. 
그 다음 모듈을 개선하고 정리하는 단계를 살펴본다.

```java
// 간단한 간단한 Args 사용법 (명령행 인수를 뿐석하는 유틸리티 클래스 Args)
public static void main(String[] args) {
  try {
    Args arg = new Args("l,p#,d*", args);
    boolean logging = arg.getBoolean('l');
    int port = arg.getInt('p');
    String directory = arg.getString('d');
    executeApplication(logging, port, directory);
  } catch (ArgsException e) {
    System.out.print("Argument error: %s\n", e.errorMessage());
  }
}
```
Args클래스는 첫 번째 매개변수는 형식 또는 스키마를 지정하는 `"l,p#,d*"`이다
두 번째 매개변수는 main으로 넘어온 명령행 인수배열 자체다
객체 생성시에 ArgsException이 발생하지않고 일단 객체가 생성된다면 질의를 던질 수 있다.

## Args 구현
```java
package com.objectmentor.utilities.args;

import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*; 
import java.util.*;

public class Args {
  private Map<Character, ArgumentMarshaler> marshalers;
  private Set<Character> argsFound;
  private ListIterator<String> currentArgument;
  
  public Args(String schema, String[] args) throws ArgsException { 
    marshalers = new HashMap<Character, ArgumentMarshaler>(); 
    argsFound = new HashSet<Character>();
    
    parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args)); 
  }
  
  private void parseSchema(String schema) throws ArgsException { 
    for (String element : schema.split(","))
      if (element.length() > 0) 
        parseSchemaElement(element.trim());
  }
  
  private void parseSchemaElement(String element) throws ArgsException { 
    char elementId = element.charAt(0);
    String elementTail = element.substring(1); validateSchemaElementId(elementId);
    if (elementTail.length() == 0)
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else if (elementTail.equals("*")) 
      marshalers.put(elementId, new StringArgumentMarshaler());
    else if (elementTail.equals("#"))
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    else if (elementTail.equals("##")) 
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    else if (elementTail.equals("[*]"))
      marshalers.put(elementId, new StringArrayArgumentMarshaler());
    else
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }
  
  private void validateSchemaElementId(char elementId) throws ArgsException { 
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null); 
  }
  
  private void parseArgumentStrings(List<String> argsList) throws ArgsException {
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
      String argString = currentArgument.next(); 
      if (argString.startsWith("-")) {
        parseArgumentCharacters(argString.substring(1)); 
      } else {
        currentArgument.previous();
        break; 
      }
    } 
  }
  
  private void parseArgumentCharacters(String argChars) throws ArgsException { 
    for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i)); 
  }
  
  private void parseArgumentCharacter(char argChar) throws ArgsException { 
    ArgumentMarshaler m = marshalers.get(argChar);
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null); 
    } else {
      argsFound.add(argChar); 
      try {
        m.set(currentArgument); 
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e; 
      }
    } 
  }
  
  public boolean has(char arg) { 
    return argsFound.contains(arg);
  }
  
  public int nextArgument() {
    return currentArgument.nextIndex();
  }
  
  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }
  
  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }
  
  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }
  
  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }
  
  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  } 
}
```
코드가 위에서 아래로 잘 읽히는 예시이다

```java
public interface ArgumentMarshaler {
  void set(Iterator<String> currentArgument) throws ArgsException;
}
```

```java
public class BooleanArgumentMarshaler implements ArgumentMarshaler { 
  private boolean booleanValue = false;
  
  public void set(Iterator<String> currentArgument) throws ArgsException { 
    booleanValue = true;
  }
  
  public static boolean getValue(ArgumentMarshaler am) {
    if (am != null && am instanceof BooleanArgumentMarshaler)
      return ((BooleanArgumentMarshaler) am).booleanValue; 
    else
      return false; 
  }
}
```

```java
import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*;

public class StringArgumentMarshaler implements ArgumentMarshaler { 
  private String stringValue = "";
  
  public void set(Iterator<String> currentArgument) throws ArgsException { 
    try {
      stringValue = currentArgument.next(); 
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_STRING); 
    }
  }
  
  public static String getValue(ArgumentMarshaler am) {
    if (am != null && am instanceof StringArgumentMarshaler)
      return ((StringArgumentMarshaler) am).stringValue; 
    else
      return ""; 
  }
}
```

```java
import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*;

public class IntegerArgumentMarshaler implements ArgumentMarshaler { 
  private int intValue = 0;
  
  public void set(Iterator<String> currentArgument) throws ArgsException { 
    String parameter = null;
    try {
      parameter = currentArgument.next();
      intValue = Integer.parseInt(parameter);
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_INTEGER);
    } catch (NumberFormatException e) {
      throw new ArgsException(INVALID_INTEGER, parameter); 
    }
  }
  
  public static int getValue(ArgumentMarshaler am) {
    if (am != null && am instanceof IntegerArgumentMarshaler)
      return ((IntegerArgumentMarshaler) am).intValue; 
    else
    return 0; 
  }
}
```

한 가지 눈에 거슬리는 점을 찾을 수 있다. 
바로 오류 코드 상수를 정의하는 부분이다.
```java
import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*;

public class ArgsException extends Exception { 
  private char errorArgumentId = '\0'; 
  private String errorParameter = null; 
  private ErrorCode errorCode = OK;
  
  public ArgsException() {}
  
  public ArgsException(String message) {super(message);}
  
  public ArgsException(ErrorCode errorCode) { 
    this.errorCode = errorCode;
  }
  
  public ArgsException(ErrorCode errorCode, String errorParameter) { 
    this.errorCode = errorCode;
    this.errorParameter = errorParameter;
  }
  
  public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
    this.errorCode = errorCode; 
    this.errorParameter = errorParameter; 
    this.errorArgumentId = errorArgumentId;
  }
  
  public char getErrorArgumentId() { 
    return errorArgumentId;
  }
  
  public void setErrorArgumentId(char errorArgumentId) { 
    this.errorArgumentId = errorArgumentId;
  }
  
  public String getErrorParameter() { 
    return errorParameter;
  }
  
  public void setErrorParameter(String errorParameter) { 
    this.errorParameter = errorParameter;
  }
  
  public ErrorCode getErrorCode() { 
    return errorCode;
  }
  
  public void setErrorCode(ErrorCode errorCode) { 
    this.errorCode = errorCode;
  }
  
  public String errorMessage() { 
    switch (errorCode) {
      case OK:
        return "TILT: Should not get here.";
      case UNEXPECTED_ARGUMENT:
        return String.format("Argument -%c unexpected.", errorArgumentId);
      case MISSING_STRING:
        return String.format("Could not find string parameter for -%c.", errorArgumentId);
      case INVALID_INTEGER:
        return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_INTEGER:
        return String.format("Could not find integer parameter for -%c.", errorArgumentId);
      case INVALID_DOUBLE:
        return String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_DOUBLE:
        return String.format("Could not find double parameter for -%c.", errorArgumentId); 
      case INVALID_ARGUMENT_NAME:
        return String.format("'%c' is not a valid argument name.", errorArgumentId);
      case INVALID_ARGUMENT_FORMAT:
        return String.format("'%s' is not a valid argument format.", errorParameter);
    }
    return ""; 
  }
  
  public enum ErrorCode {
    OK, INVALID_ARGUMENT_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME, 
    MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, MISSING_DOUBLE, INVALID_DOUBLE
  }
}
```
단순한 개념을 구현하는데 코드가 너무 많이필요하다고 생각될 수도 있지만, 장황한 언어인 자바를 사용하므로 
정적타입언어로 타입시스템을 만족하려면 많은 단어가 필요하다

하지만 이름을 붙인 방법, 함수 크기, 코드 형식에 주목해본다면 전반적으로 깔끔한 구조이다.
에를들어 날짜 인수나 복소수 인수 등 새로운 인수 유형을 추가하는 방법이 명백하다.
ArgumentMarshaler에서 새 클래스를 파생해 getXXX함수를 추가한 후 parseSchemaElement 함수에 새 case문만 추가하면 끝이다.

### 어떻게 짯느냐고?
처음부터 위처럼 구현하지 않았다. 깨긋한 프로그램은 한방에 뚝딱 내놓아지지 않는다.
프로그래밍은 과학보다 공예(craft)에 가깝다. 깨끗한 코드를 짜려면 먼저 지저분한 코드를 짠 뒤 정리해야 한다는 의미이다.


## Args: 1차 초안
처음 짯던 Args 클래스이다. 코드는 '돌아가지만' 엉망이다.
```java
import java.text.ParseException; 
import java.util.*;

public class Args {
  private String schema;
  private String[] args;
  private boolean valid = true;
  private Set<Character> unexpectedArguments = new TreeSet<Character>(); 
  private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();
  private Map<Character, String> stringArgs = new HashMap<Character, String>(); 
  private Map<Character, Integer> intArgs = new HashMap<Character, Integer>(); 
  private Set<Character> argsFound = new HashSet<Character>();
  private int currentArgument;
  private char errorArgumentId = '\0';
  private String errorParameter = "TILT";
  private ErrorCode errorCode = ErrorCode.OK;
  
  private enum ErrorCode {
    OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT}
    
  public Args(String schema, String[] args) throws ParseException { 
    this.schema = schema;
    this.args = args;
    valid = parse();
  }
  
  private boolean parse() throws ParseException { 
    if (schema.length() == 0 && args.length == 0)
      return true; 
    parseSchema(); 
    try {
      parseArguments();
    } catch (ArgsException e) {
    }
    return valid;
  }
  
  private boolean parseSchema() throws ParseException { 
    for (String element : schema.split(",")) {
      if (element.length() > 0) {
        String trimmedElement = element.trim(); 
        parseSchemaElement(trimmedElement);
      } 
    }
    return true; 
  }
  
  private void parseSchemaElement(String element) throws ParseException { 
    char elementId = element.charAt(0);
    String elementTail = element.substring(1); 
    validateSchemaElementId(elementId);
    if (isBooleanSchemaElement(elementTail)) 
      parseBooleanSchemaElement(elementId);
    else if (isStringSchemaElement(elementTail)) 
      parseStringSchemaElement(elementId);
    else if (isIntegerSchemaElement(elementTail)) 
      parseIntegerSchemaElement(elementId);
    else
      throw new ParseException(String.format("Argument: %c has invalid format: %s.", 
        elementId, elementTail), 0);
    } 
  }
    
  private void validateSchemaElementId(char elementId) throws ParseException { 
    if (!Character.isLetter(elementId)) {
      throw new ParseException("Bad character:" + elementId + "in Args format: " + schema, 0);
    }
  }
  
  private void parseBooleanSchemaElement(char elementId) { 
    booleanArgs.put(elementId, false);
  }
  
  private void parseIntegerSchemaElement(char elementId) { 
    intArgs.put(elementId, 0);
  }
  
  private void parseStringSchemaElement(char elementId) { 
    stringArgs.put(elementId, "");
  }
  
  private boolean isStringSchemaElement(String elementTail) { 
    return elementTail.equals("*");
  }
  
  private boolean isBooleanSchemaElement(String elementTail) { 
    return elementTail.length() == 0;
  }
  
  private boolean isIntegerSchemaElement(String elementTail) { 
    return elementTail.equals("#");
  }
  
  private boolean parseArguments() throws ArgsException {
    for (currentArgument = 0; currentArgument < args.length; currentArgument++) {
      String arg = args[currentArgument];
      parseArgument(arg); 
    }
    return true; 
  }
  
  private void parseArgument(String arg) throws ArgsException { 
    if (arg.startsWith("-"))
      parseElements(arg); 
  }
  
  private void parseElements(String arg) throws ArgsException { 
    for (int i = 1; i < arg.length(); i++)
      parseElement(arg.charAt(i)); 
  }
  
  private void parseElement(char argChar) throws ArgsException { 
    if (setArgument(argChar))
      argsFound.add(argChar); 
    else 
      unexpectedArguments.add(argChar); 
      errorCode = ErrorCode.UNEXPECTED_ARGUMENT; 
      valid = false;
  }
  
  private boolean setArgument(char argChar) throws ArgsException { 
    if (isBooleanArg(argChar))
      setBooleanArg(argChar, true); 
    else if (isStringArg(argChar))
      setStringArg(argChar); 
    else if (isIntArg(argChar))
      setIntArg(argChar); 
    else
      return false;
    
    return true; 
  }
  
  private boolean isIntArg(char argChar) {
    return intArgs.containsKey(argChar);
  }
  
  private void setIntArg(char argChar) throws ArgsException { 
    currentArgument++;
    String parameter = null;
    try {
      parameter = args[currentArgument];
      intArgs.put(argChar, new Integer(parameter)); 
    } catch (ArrayIndexOutOfBoundsException e) {
      valid = false;
      errorArgumentId = argChar;
      errorCode = ErrorCode.MISSING_INTEGER;
      throw new ArgsException();
    } catch (NumberFormatException e) {
      valid = false;
      errorArgumentId = argChar; 
      errorParameter = parameter;
      errorCode = ErrorCode.INVALID_INTEGER; 
      throw new ArgsException();
    } 
  }
  
  private void setStringArg(char argChar) throws ArgsException { 
    currentArgument++;
    try {
      stringArgs.put(argChar, args[currentArgument]); 
    } catch (ArrayIndexOutOfBoundsException e) {
      valid = false;
      errorArgumentId = argChar;
      errorCode = ErrorCode.MISSING_STRING; 
      throw new ArgsException();
    } 
  }
  
  private boolean isStringArg(char argChar) { 
    return stringArgs.containsKey(argChar);
  }
  
  private void setBooleanArg(char argChar, boolean value) { 
    booleanArgs.put(argChar, value);
  }
  
  private boolean isBooleanArg(char argChar) { 
    return booleanArgs.containsKey(argChar);
  }
  
  public int cardinality() { 
    return argsFound.size();
  }
  
  public String usage() { 
    if (schema.length() > 0)
      return "-[" + schema + "]"; 
    else
      return ""; 
  }
  
  public String errorMessage() throws Exception { 
    switch (errorCode) {
      case OK:
        throw new Exception("TILT: Should not get here.");
      case UNEXPECTED_ARGUMENT:
        return unexpectedArgumentMessage();
      case MISSING_STRING:
        return String.format("Could not find string parameter for -%c.", errorArgumentId);
      case INVALID_INTEGER:
        return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_INTEGER:
        return String.format("Could not find integer parameter for -%c.", errorArgumentId);
    }
    return ""; 
  }
  
  private String unexpectedArgumentMessage() {
    StringBuffer message = new StringBuffer("Argument(s) -"); 
    for (char c : unexpectedArguments) {
      message.append(c); 
    }
    message.append(" unexpected.");
    
    return message.toString(); 
  }
  
  private boolean falseIfNull(Boolean b) { 
    return b != null && b;
  }
  
  private int zeroIfNull(Integer i) { 
    return i == null ? 0 : i;
  }
  
  private String blankIfNull(String s) { 
    return s == null ? "" : s;
  }
  
  public String getString(char arg) { 
    return blankIfNull(stringArgs.get(arg));
  }
  
  public int getInt(char arg) {
    return zeroIfNull(intArgs.get(arg));
  }
  
  public boolean getBoolean(char arg) { 
    return falseIfNull(booleanArgs.get(arg));
  }
  
  public boolean has(char arg) { 
    return argsFound.contains(arg);
  }
  
  public boolean isValid() { 
    return valid;
  }
  
  private class ArgsException extends Exception {
  } 
}
```
처음부터 지저분한 코드르 짜려는 생각은 없었지만
Boolean 인수만 지원하던 초기 버전에서 String, Integer인수 유형을 추가하면서 재앙이 시작됐다.

### 그래서 멈췄다
추가할 인수 유형이 늘어나면서 더 이상 손대기 어렵다고 판단되었고, 코드 구조를 유지보수하기 좋은 상태로 만들려면 지금이 적기라 판단했다.

그래서 기능을 더 이상 추가하지 않기로 결정하고 리펙터링을 시작했다

### 점진적으로 개선하다
프로그램을 망치는 가장 좋은 방법중 하나는 개선이라는 이름 아래 구조를 크게 뒤집는 행위이다.
어떤 프로그램은 그저 그런 '개선'에서 결코 회복하지 못한다. '개선'전과 똑같이 프로그램을 돌리기가 아주 어렵기 때문이다.

그래서 **테스트 주도 개발(Test-Driven-Development, TDD)**라는 기법을 사용했다.
TDD는 언제 어느때라도 시스템이 돌아가야 한다는 원칙을 따른다.
다시말해, TDD는 시스템을 망가뜨리는 변경을 허용하지 않는다.
변경을 가한 후에도 시스템이 변경 전과 똑같이 돌아가야 한다는 말이다.

변경 전후 시스템이 똑같이 돌아간다는 사실을 확인하려면 언제든 실행이 가능한 자동화된 테스트슈트가 필요하다.

가장 먼저 나는 기존 코드 끝에 ArgumentMarshaler 클래스의 골격을 추가했다.
```java
private class ArgumentMarshaler { 
  private boolean booleanValue = false;

  public void setBoolean(boolean value) { 
    booleanValue = value;
  }
  
  public boolean getBoolean() {return booleanValue;} 
}

private class BooleanArgumentMarshaler extends ArgumentMarshaler { }
private class StringArgumentMarshaler extends ArgumentMarshaler { }
private class IntegerArgumentMarshaler extends ArgumentMarshaler { }
```
그리고 코드를 최소로 건드리는, 가장 단순한 변경을 가했다.
구체적으로는 Boolean 인수를 저장하는 HashMap에서 Boolean 인수 유형을 ArgumentMarshaler 유형으로 바꿨다

```java
private Map<Character, ArgumentMarshaler> booleanArgs
     = new HashMap<Character, ArgumentMarshaler>();
```

```java
...

private void parseBooleanSchemaElement(char elementId) {
  booleanArgs.put(elementId, new BooleanArgumentMarshaler());
}
  
...

private void setBooleanArg(char argChar, boolean value) {
  booleanArgs.get(argChar).setBoolean(value);
}
  
...

public boolean getBoolean(char arg) {
  Args.ArgumentMarshaler am = booleanArgs.get(arg);
  return am != null && am.getBoolean();
}
```


## String 인수
String 인수를 추가하는 과정은 boolean 인수와 매우 유사했다.
HashMap을 변경한 후 parse, set, get 함수를 고쳤다.
일단 각 인수 유형을 처리하는 코드를 모두 ArgumentMarshaler 클래스에 넣고 나서 그 파생클래스를 만들어 코드를 분리할 게획을 세우고 진행했다.

해당 부분을 리펙토링하고 난 후 남는 문제는 예외 코드 부분이다. 예외코드는 아주 흉할뿐더러 사실상 Args 클래스에 속하지도 않는다.
게다가 ParseException을 던지지만 ParseException은 Args클래스에 속하지 않는다.
그러므로 모든 예외를 하나로 모아 ArgsException 클래스를 만든 후 독자 모듈로 옮긴다.
이렇게 되면 Args 모듈에서 예외/오류 처리 코드를 완벽하게 분리할 수 있다.


## 최종 코드
```java
public class ArgsException extends Exception { 
  private char errorArgumentId = '\0'; 
  private String errorParameter = "TILT"; 
  private ErrorCode errorCode = ErrorCode.OK;
  
  public ArgsException() {}
  
  public ArgsException(String message) {super(message);}
  
  public ArgsException(ErrorCode errorCode) { 
    this.errorCode = errorCode;
  }
  
  public ArgsException(ErrorCode errorCode, String errorParameter) { 
    this.errorCode = errorCode;
    this.errorParameter = errorParameter;
  }
  
  public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
    this.errorCode = errorCode; 
    this.errorParameter = errorParameter; 
    this.errorArgumentId = errorArgumentId;
  }
  
  public char getErrorArgumentId() { 
    return errorArgumentId;
  }
  
  public void setErrorArgumentId(char errorArgumentId) { 
    this.errorArgumentId = errorArgumentId;
  }
  
  public String getErrorParameter() { 
    return errorParameter;
  }
  
  public void setErrorParameter(String errorParameter) {  
    this.errorParameter = errorParameter;
  }
  
  public ErrorCode getErrorCode() { 
    return errorCode;
  }
  
  public void setErrorCode(ErrorCode errorCode) { 
    this.errorCode = errorCode;
  }
  
  public String errorMessage() throws Exception { 
    switch (errorCode) {
      case OK:
        throw new Exception("TILT: Should not get here.");
      case UNEXPECTED_ARGUMENT:
        return String.format("Argument -%c unexpected.", errorArgumentId);
      case MISSING_STRING:
        return String.format("Could not find string parameter for -%c.", errorArgumentId);
      case INVALID_INTEGER:
        return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_INTEGER:
        return String.format("Could not find integer parameter for -%c.", errorArgumentId);
      case INVALID_DOUBLE:
        return String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
      case MISSING_DOUBLE:
        return String.format("Could not find double parameter for -%c.", errorArgumentId);
    }
    return ""; 
  }
  
  public enum ErrorCode {
    OK, INVALID_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME, MISSING_STRING,
    MISSING_INTEGER, INVALID_INTEGER,
    MISSING_DOUBLE, INVALID_DOUBLE
  }
}
```

```java
public class Args {
  private String schema;
  private Map<Character, ArgumentMarshaler> marshalers = new HashMap<Character, ArgumentMarshaler>();
  private Set<Character> argsFound = new HashSet<Character>(); 
  private Iterator<String> currentArgument;
  private List<String> argsList;
  
  public Args(String schema, String[] args) throws ArgsException { 
    this.schema = schema;
    argsList = Arrays.asList(args);
    parse();
  }
  
  private void parse() throws ArgsException { 
    parseSchema();
    parseArguments();
  }
  
  private boolean parseSchema() throws ArgsException {
    for (String element : schema.split(",")) { 
      if (element.length() > 0) {
        parseSchemaElement(element.trim()); 
      }
    }
    return true; 
  }
  
  private void parseSchemaElement(String element) throws ArgsException { 
    char elementId = element.charAt(0);
    String elementTail = element.substring(1); 
    validateSchemaElementId(elementId);
    if (elementTail.length() == 0)
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else if (elementTail.equals("*")) 
      marshalers.put(elementId, new StringArgumentMarshaler());
    else if (elementTail.equals("#"))
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    else if (elementTail.equals("##")) 
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    else
      throw new ArgsException(ArgsException.ErrorCode.INVALID_FORMAT, elementId, elementTail);
      
  private void validateSchemaElementId(char elementId) throws ArgsException { 
    if (!Character.isLetter(elementId)) {
      throw new ArgsException(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, elementId, null);
    } 
  }
  
  private void parseArguments() throws ArgsException {
    for (currentArgument = argsList.iterator(); currentArgument.hasNext();) {
      String arg = currentArgument.next();
      parseArgument(arg); 
    }
  }
  
  private void parseArgument(String arg) throws ArgsException { 
    if (arg.startsWith("-"))
      parseElements(arg); 
  }
  
  private void parseElements(String arg) throws ArgsException { 
    for (int i = 1; i < arg.length(); i++)
      parseElement(arg.charAt(i)); 
  }
  
  private void parseElement(char argChar) throws ArgsException { 
    if (setArgument(argChar))
      argsFound.add(argChar); 
    else 
      throw new ArgsException(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, argChar, null);
  } 
  
  private boolean setArgument(char argChar) throws ArgsException { 
    ArgumentMarshaler m = marshalers.get(argChar);
    if (m == null)
      return false; 
    try {
      m.set(currentArgument);
      return true;
    } catch (ArgsException e) {
      e.setErrorArgumentId(argChar);
      throw e; 
    }
  }
  
  public int cardinality() { 
    return argsFound.size();
  }
  
  public String usage() { 
    if (schema.length() > 0)
      return "-[" + schema + "]"; 
    else
      return ""; 
  }
  
  public boolean getBoolean(char arg) { 
    ArgumentMarshaler am = marshalers.get(arg); 
    boolean b = false;
    try {
      b = am != null && (Boolean) am.get(); 
    } catch (ClassCastException e) {
      b = false; 
    }
    return b; 
  }
  
  public String getString(char arg) { 
    ArgumentMarshaler am = marshalers.get(arg); 
    try {
      return am == null ? "" : (String) am.get(); 
    } catch (ClassCastException e) {
      return ""; 
    }
  }
  
  public int getInt(char arg) { 
    ArgumentMarshaler am = marshalers.get(arg); 
    try {
      return am == null ? 0 : (Integer) am.get(); 
    } catch (Exception e) {
      return 0; 
    }
  }
  
  public double getDouble(char arg) { 
    ArgumentMarshaler am = marshalers.get(arg); 
    try {
      return am == null ? 0 : (Double) am.get(); 
    } catch (Exception e) {
      return 0.0; 
    }
  }
  
  public boolean has(char arg) { 
    return argsFound.contains(arg);
  } 
}
```
Args 클래스에서 코드 중복을 최소화 하고, 상당한 코드를 Args 클래스에서 ArgsException 클래스로 옮겼다.
또한 ArgumentMarshaler 클래스를 통해 여러 인수에 대한 추구 확장성을 꾀했다.

**소프트웨어 설계는 분할만 잘해도 품질이 크게 높아진다. 적절한 장소를 만들어 코드만 분리해도 설계가 좋아진다.
관심사를 분리하면 코드를 이해하고 보수하기 훨씬 더 쉬워진다**


## 결론
그저 돌아가는 코드만으로는 부족하다. 돌아가는 코드가 심하게 망가지는 사례는 흔하다.
단순히 돌아가는 코드에 만족하는 프로그래머는 전문가 정신이 부족하다.
**나쁜 코드보다 더 오랫동안 더 심각하게 개발 프로젝트에 악영향을 미치는 요인도 없다.
나쁜일정은 다시짜면 된다. 나쁜 요구사항은 다시정의하면 된다. 나쁜팀 역학은 복구하면된다.
하지만 나쁜 코드는 썩어 문드러진다.**

나쁜 코드는 깨끗한 코드로 개선하려면 비용이 엄청 많이든다. 서로 얽히고 설켜 뒤엉키고 숨겨진 의존성이 수도없이 생긴다.
하지만 처음부터 코드를 깨긋하게 유지하기란 상대적으로 쉽다. 아침에 엉망으로 만든 코드를 오후에 정리하기는 어렵지않다.
더욱이 5분전에 엉망으로 만든 코드는 지금 당장 정리하기 아주 쉽다.

코드는 언제나 최대한 깔금하고 단순하게 정리하자. 절대로 썩어가게 방치하면 안된다.