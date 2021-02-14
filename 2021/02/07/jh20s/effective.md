item 69. 예외는 진자 예외상황에만 사용하라.

예외는 오직 예외상황에서만 써야하낟. 절대로 일상적인 제어 흐름용으로 쓰여선 안된다.
잘 설계 된 API라면 클라이언트가 정상적인 제어 흐름에서 예외를 사용할 일이 없게 해야한다.

상태 검사 메서드, 옵셔널, 특정ㄱ밧 중 하나를 선택하는 지침

1. 외부 동기화 없이 여러 스레드가 동시에 접근할 수 있거나 외부요인으로 상태가변할 수 있다면
옵셔널이나 특정 값을 사용한다. 상태 검사 메서드와 상태의존적 메서드 호출 사이에
객체의 상태가 변할 수 있기 때문이다.
2. 성능이 중요한 상황에서 상태검사 메서드가 상태 의존적 메서더의작업 일부를 중복수행한다면
옵셔널이나 특정 값을 선택한다.
3. 다른 모든 경우엔 상태 검사 메서드 방식이 조금 더 낫다고 할 수 있다. 가독성이 살짝 더 좋고
잘못 사용했을때 발견하기가 쉽다.

item 70. 복구할 수 있는 상황에는 검사 예뢰를
프로그래밍 오류에는 런타임 예외를 사용하라.

자바에서는 문제 상황을 알리는 타입으로 검사예외, 런타임예외, 에러 이렇게 3가지가 있다.
호출하는 쪽에서 복구하리라 여겨지는 상황이라면 검사 예외를 사용하라.
메서드 선언에 포함된 검사 예외 각각은 그 메서드를 호출했을 때 발생할 수 있는 유력한 결과임을 API 사용자에게 알려주는 것이다.

비검사 throwable은 두가지로 런타임 예외와 에러다.
이 둘은 프로그램에서 잡을 필요가 없거나 혹은 통상 적으로 잡지 말아야 한다.
프로그래밍 오류를 나타낼때에는 런타임 예외를 사용하자.

Error 클래스를 상속해 하위 클래스를 만드는 일은 자제하자 ->  할순 있지만 업계에 널리 펴진 규약
비검사 throwable은 모두 RuntimeException의 하위 클래스여야한다. 

Exception,RuntimeExceiption,Error를 상속하지 않는 throwable을 만들수 도 있다.
그러나 정상적인 검사예외보다 나을게 하나 없으니 사용하지 마라.

예외상황이 발생한 정보를 코드 형태로 전달하는데 이런 메서드가 없으면 
프로그래머가 파싱해 정보를 빼내야한다. -> 에러 메시지에대해서는 반환하는 메서드가 있는게 바람직한다

item 71. 필요없는 검사 예외 사용은 피하라.

검사 예외는 발생한 문제를 프로그래머가 처리하여 안정성을 높이게끔 해준다.
검사 예외를 던지는 메서드는 스트림 안에서 직접 사용할 수 없기때문에 자버8부터 부담이 커졌다.
검사예외와 비검사예외 둘중 어디에도 해당되지 않는다면 비검사 예외를 사용하는게 좋다.

검사 예외가 단 하나 뿐이라면 그 API를 사용하기 위해 사용자는 try catch블록을 추가해야 하고 스트림에서 직접 사용하지 못하게 된다.
검사 예외를 회피하는 손쉬은 방법은 적절한 결과 타입을 담은 옵셔널을 반환하는 것이다.
검사 예외를 던지는 대신 단순히 빈 옵셔널을 반환하면 된다.
 
API호출자가 예외상황에서 복구할 방법이 없다면 비검사 예외를 던지자.

item 72. 표준 예외를 사용하라.

표준예외를 사용하면 API가 사용하기 쉬워진다.
예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.

Exception, RuntimeException, Throwable, Error는 재사용하지 말자. 이 클래스들은 추상클래스라고 생각해야한다.


Illegal ArumentionException : 허용하지 않는 값이 인수로 건네졌을때
IllegalStateException : 객체가 메서드를 수행하기에 적절하지 않은 상태일 때
NullPointerException : null을 허용하지 않는 메서드에 null을 건넸을때
IndexOutOfBoundsException : 인덱스가 범위를 넘어 섰을때
ConcurrentModificationException : 허용하지 않는 동시 수정이 발견됐을 때
Unsupported PoerationException : 호출한 메서드를 지원하지 않을때
