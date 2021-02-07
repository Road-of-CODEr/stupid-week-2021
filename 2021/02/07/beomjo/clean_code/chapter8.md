# Chapter8 - 경계 

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Chapter8 - 경계](#chapter8-경계)
  - [외부 코드 사용하기](#외부-코드-사용하기)
  - [경계 살피고 익히기](#경계-살피고-익히기)
  - [학습 테스트는 공짜 이상이다.](#학습-테스트는-공짜-이상이다)
  - [아직 존재하지 않는 코드를 사용하기](#아직-존재하지-않는-코드를-사용하기)
  - [깨끗한 경계](#깨끗한-경계)

<!-- /code_chunk_output -->

## 외부 코드 사용하기
- 패키지 제공자나 프레임워크 제공자는 적용성을 최대한 넓히려 애쓴다. 더 많은 환경에서 돌아가야 더 많은 고객이 구매하니까.
- 사용자는 자신의 요구에 집중하는 인터페이스를 바란다.
- 이런 긴장으로 인해 시스템 경계에서 문제가 생길 소지가 많다.
```
• clear() void – Map
• containsKey(Object key) boolean – Map
• containsValue(Object value) boolean – Map
• entrySet() Set – Map
• equals(Object o) boolean – Map
• get(Object key) Object – Map
• getClass() Class<? extends Object> – Object
• hashCode() int – Map
• isEmpty() boolean – Map
• keySet() Set – Map
• notify() void – Object
• notifyAll() void – Object
• put(Object key, Object value) Object – Map
• putAll(Map t) void – Map
• remove(Object key) Object – Map
• size() int – Map
• toString() String – Object
• values() Collection – Map
• wait() void – Object
• wait(long timeout) void – Object
• wait(long timeout, int nanos) void – Object
```
- 한 예로 java.util.Map이 있다. Map은 굉장히 다양한 인터페이스로 수많은 기능을 제공한다.
- Map이 제공하는 기능성과 유연성은 확실히 유용하지만 그만큼 위험도 크다.
    - 생각지 못한 추상 메서드
    - 객체 유형을 제한하지 않음
    - 사용자에게 필요하지 않은 기능까지 제공 
```
Map sensors = new HashMap();
Map<String, Sensor> sensors = new HashMap<Sensor>();
...
Sensor s = sensors.get(sensorId);
```
- 위 예제의 경우 간단한 Sensor 인스턴스를 저장 및 조회를 위해 Map을 사용하고 있지만 clear() 와 같은 필요하지 않은 기능까지 제공하게 된다.
```
public class Sensors {
    private Map sensors = new HashMap();

    public Sensor getById(String id) {
        return (Sensor) sensors.get(id);
    }

    //snip
}
```
- 경계 인터페이스인 Map을 Sensors 안으로 숨김으로 Map 인터페이스의 변화나 제너릭스의 사용 유무에 영향을 받지 않게 된다. Sensors 클래스 안에서 객체 유형을 관리하고 변환하기 때문이다.
- Map과 같은 경계 인터페이스를 사용할 때는 이를 이요하는 클래스나 클래스 계열 밖으로 노출되지 않도록 주의한다. 

## 경계 살피고 익히기
- 외부 코드를 익히거나 통합하기는 어렵다. 두 가지를 동시에 하기는 두 배나 어렵다.
- 학습 테스트라 불리는 방식으로, 간단한 테스트 케이스를 작성해 외부 코드를 익히는 방식으로 접근하면 좋을것 같다.

## 학습 테스트는 공짜 이상이다.
- 학습 테스트에 드는 비용은 없다. 오히려 필요한 지식만 확보하는 손쉬운 방법이고, 이해도를 높여주는 정확한 실험이다.

## 아직 존재하지 않는 코드를 사용하기
- 경계와 관련해 또 다른 유형은 아는 코드와 모르는 코드를 분리하는 경계다.
- 때로는 지식이 경계를 너머 미치지 못하는 코드 영역도 있다.
- 때로는 (적어도 지금은) 알려고 해도 알 수가 없다.
- 때로는 더 이상 내다보지 않기로 결정한다.
- 아직 구현되지 않은 클래스에 대해서 Fake 클래스를 사용할 수 있다.

## 깨끗한 경계
- 소프트웨어 설계가 우수하다면 변경하는데 많은 투자와 재작업이 필요하지 않다.
- 경계에 위치하는 코드는 깔끔히 분리한다.
- 통제가 불가능한 외부 패키지에 의존하느 대신 통제가 가능한 우리 코드에 의존하는 편이 훨씬 좋다.
- 외부 패키지를 호출하는 코드를 가능한 줄여 경계를 관리하자. 새로운 클래스로 경계를 감싸거나, ADAPTER 패턴을 사용해 우리가 원하는 인터페이스를 패키지가 제공하는 인터페이스로 변환하자.