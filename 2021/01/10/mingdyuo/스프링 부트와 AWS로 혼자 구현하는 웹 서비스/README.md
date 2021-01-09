## 스프링 부트와 AWS로 혼자 구현하는 웹 서비스



1. **인텔리 제이로 스프링부트 시작하기**

   1.1 인텔리제이 소개

   1.2 인텔리제이 설치하기

   1.3 인텔리제이 커뮤니티에서 프로젝트 생성하기

   1.4 그레이들 프로젝트를 스프링부트 프로젝트로 변경하기

> 의존성 라이브러리 원격 저장소
>
> 1. mavenCenter()
>    - 기본적으로 많이 사용하는 것
>    - 본인이 만든 라이브러리 업로드 시 많은 설정과 과정이 필요
>
> 2. jcenter()
>    - 라이브러리 업로드를 간편화한 것
>    - jcenter에 업로드 시 mavenCenteral에도 업로드 되도록 자동화 가능

   1.5 인텔리에이에서 깃과 깃허브 사용하기



2. **스프링부트에서 테스트 코드를 작성하자**

   2.1 테스트 코드 소개

   > **TDD** 와 **단위 테스트 (unit test)**는 다르다
   >
   > 1. TDD
   >
   >    테스트가 주도하는 개발, 테스트 코드를 먼저 작성하며 시작함
   >
   >    | Red                                  | Green                                      | Refactor                                        |
   >    | ------------------------------------ | ------------------------------------------ | ----------------------------------------------- |
   >    | 항상 실패하는 테스트를 먼저 작성하고 | 테스트가 통과하는 프로덕션 코드를 작성하고 | 테스트가 통과하면 프로덕션 코드를 리팩토링한다. |
   >
   > 2. 단위 테스트 
   >
   >    TDD의 첫 번째 단계인 **기능 단위의 테스트 코드를 작성**하는 것 의미
   >
   >    테스트 코드를 꼭 먼저 작성하지도 X, 리팩토링 포함 X
   >
   > [TDD 실천법과 도구](https://repo.yona.io/doortts/blog/issue/1)

   - 테스트 코드를 작성하는 이유

     1. 개발 단계 초기에 문제 발견을 도와줌

     2. 개발자가 만든 기능을 안전하게 보호해줌

        기능 B를 추가했을 때 잘 되던 기능 A에 문제가 생길 경우 빠른 발견 가능

     3. 프린트 찍어서 눈으로 검증하는 것보다 낫다 자동검증이 됨

     4. 단위 테스트 자체가 문서 역할 가능

   - 테스트 코드 작성 프레임워크

     1. Java - JUnit
     2. DB - DBUnit
     3. C++ - CppUnit
     4. .net - NUnit

   

   2.2 Hello Controller 테스트 코드 작성하기

   > 스프링 부트에서는 **내장 WAS 사용을 권장**함
   >
   > - 언제 어디서나 같은 환경에서 스트링 부트를 배포할 수 있기 때문
   >
   > - 외장 WAS를 쓴다고 하면, 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야 함
   >
   >   새로운 서버 추가시 모든 서버가 같은 환경 구축해야 함 → 노답이다.
   >
   > `src/main/java/com/mingdyuo/book/springboot/web/HelloController`
   >
   > 1. `@RestController`
   >    - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어줌
   >    - 예전에는 `@ResponseBody`를 각 메소드마다 선언 → 이를 한번에 사용할 수 있도록 해줌
   > 2. `@GetMapping`
   >    - HTTP Method인 Get의 요청을 받을 수 있는 API 만들어줌
   >    - 예전에는 `@RequestMapping`(method = RequestMethod.GET)으로 사용됨
   >    - `/hello`로 요청이 오면 문자열 `"hello"`를 반환하는 기능 만들어준 것
   >
   > `src/test/java/com/mingdyuo/book/springboot/web/HelloControllerTest`
   >
   > 1. `@RunWith(SpringRunner.class)`
   >
   >    - 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자를 실행
   >
   >    - 여기서는 SpringRunner라는 스프링 실행자를 사용
   >
   >      즉, 스프링 부트 테스트와 JUnit 사이에서 연결자 역할
   >
   > 2. `@WebMvcTest`
   >
   >    - 여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
   >    - 선언할 경우 `@Controller`, `@ControllerAdvice`등을 사용할 수 있음
   >    - 단 `@Service`, `@Component`, `@Repository` 등은 사용할 수 없음
   >    - 여기서는 컨트롤러만 사용하기 때문에 선언함
   >
   > 3. `@Autowired`
   >
   >    스프링이 관리하는 빈(Bean)을 주입받는다.
   >
   > 4. `private MockMvc mvc`
   >
   >    - 웹 API를 테스트할 때 사용
   >    - 스프링 MVC 테스트의 시작점
   >    - 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있음
   >
   > 5. `mvc.perform(get("/hello"))`
   >
   >    - MockMvc를 통해 `/hello` 주소로 HTTP GET 요청을 함
   >    - 체이닝(쩜)이 지원되므로 아래와 같이 여러 검증 기능을 이어서 선언 가능
   >
   > 6. `.andExpect(status().isOk())`
   >
   >    - `mvc.perform`의 결과를 검증함
   >
   >    - HTTP header의 status를 검증함
   >
   >      → 200(OK)인지 아닌지 검증
   >
   > 7. `.andExpect(content().string(hello))`
   >
   >    - `mvc.perform`의 결과를 검증
   >    - 응답 본문의 내용을 검증
   >    - Controller에서 "hello"를 리턴하기 때문에 이값이 맞는지 검증함

   * 절대 수동으로 검증 후 테스트 코드를 작성하지 않는다.

     **테스트 코드로 먼저 검증 후**, 정말 못 믿겠을 때 프로젝트를 실행해 확인한다.

   아씨 모니터 사고싶다...

   

   2.3 롬복 소개 및 설치하기

   2.4 Hello Controller 코드를 롬복으로 전환하기

   > `src/main/java/com/mingdyuo/book/springboot/web/dto/HelloResponseDto`
   >
   > 1. `@Getter`
   >
   >    선언된 모든 필드의 get 메소드를 생성해줌
   >
   > 2. `@RequiredArgsConstructor`
   >
   >    - 선언된 모든 final 필드가 포함된 생성자를 생성해 준다
   >    - final이 없는 필드는 포함 X

   > `src/test/java/com/mingdyuo/book/springboot/web/dto/HelloResponseDtoTest`
   >
   > 1. `assertThat`
   >
   >    - `assertj`라는 테스트 검증 라이브러리의 검증 메소드
   >
   >    - 검증하고 싶은 대상을 메소드 인자로 받음
   >
   >    - 메소드 체이닝이 지원
   >
   >      `isEqualTo`와 같이 메소드를 이어서 사용 가능
   >
   > 2. `isEqualTo`
   >
   >    - `assertj`의 동등 비교 메소드
   >    - `assertThat`에 있는 값과  `isEqualTo`의 값을 비교해서 같을 때만 성공

   - `Junit`과 비교했을 때 `assertj`의 장점

     1. `CodeMatchers`와 달리 추가적인 라이브러리가 필요하지 않음

        `Junit`의 `assertThat`을 쓰면 `is()`와 같은 `CoreMatchers` 라이브러리가 필요함

     2. 자동완성이 좀 더 확실하게 지원됨

     3. [참고 영상](https://youtu.be/zLx_fI24UXM)

   > 1. `param`
   >    - API 테스트 할 때 사용될 요청 파라미터를 설정
   >    - 단 값은 `String`만 허용
   >    - 따라서 숫자/날짜 등의 데이터도 등록시에는 문자열로 변경해야 ㅎ ㅏㅁ
   > 2. `jsonPath`
   >    - JSON 응답값을 필드별로 검증할 수 있는 메소드
   >    - `$`를 기준으로 필드명을 명시
   >    - 예를 들어 `name, amount`를 검증 시 `$.name, $.amount`로 검증
   
   
   
3. **스프링부트에서 JPA로 데이터베이스 다뤄보자**

   JPA는 자바 표준 ORM(Object Relational Mapping) 기술임. 관계형 데이터베이스를 사용하면서 객체지향 프로그래밍을 하기 위해 사용한다. 

   3.1 JPA 소개

   - 현대의 웹 애플리케이션에서 RDB는 매우 중요한 요소임. (Oracle MySQL, MSSQL를 통해 조작) 따라서 객체를 관계형 데이터베이스에서 관리하는 것이 아주 중요하다. 

   - RDB가 웹 서비스의 중심이 되면서 모든 코드가 SQL 중심이 되어가고 있음. 이는 RDB가 SQL만 인식할 수 있기 때문인데, 이 때문에 각 테이블마다 기본적인 CRUD(Create, Read, Update, Delete)를 매번 생성해야 함. 따라서 개발자가 아무리 클래스를 잘 설계해도 SQL을 통해서만 디비에 저장 조회가 가능함. SQL은 피할 수 없음. 엄청 많은 테이블에 대해서 단순 작업을 만복해야 함

   -  또한 패러다임 불일치 문제도 발생함. 

     > **관계형 데이터베이스** 어떻게 데이터를 저장할지에 초점
     >
     > **객체 지향 프로그래밍**  메시지를 기반으로 기능과 속성을 한 곳에서 관리
     >
     > → 시작점 자체가 다르다.

   - 상속, 1:N 등 다양한 객체 모델링을 DB로 구현할 수 없기 때문에 웹 애플리케이션을 개발할 때에는 점점 데이터 베이스 모델링에 집중하게 된다.

     **→ 이 중간에서 패러다임 일치를 시켜 주는 것이 JPA**

     - 개발자가 객체 지향 프로그래밍을 하면 JPA가 이를 RDB에 맞게 SQL을 대신 생성한다.
     - SQL에 종속적인 개발을 하지 않아도 됨

   - JPA는 인터페이스이기 때문에 구현체가 필요하지만, Spring에서 사용할 때에는 구현체를 추상화시킨 Spring Data JPA라는 모듈을 이용하여 다룬다.

     **JPA ← Hibernate ← Spring Data JPA**

   - Spring Data JPA의 장점

     1. 구현체 교체의 용이성

        hibernate과 같은 구현체를 다른 것으로 바꿀 때 편리

     2. 저장소 교체의 용이성

        RDB외에 다른 저장소로 교체할 때 편리

        - 트래픽이 많아지면 RDB로는 감당이 안될 수 있음
        - 이 때 MongoDB로 교체가 필요하다면, Spring Data JPA에서 Spring Data MongoDB로 **의존성만 교체하면 된다.**
        - 이는 Spring Data의 하위 프로젝트는 기본적인 CRUD의 인터페이스가 같기 때문임기본적인 기능은 변경할 것이 없음

   - JPA를 잘 쓰려면 객체 지향 프로그래밍과 RDB를 둘 다 이해해야 함. 러닝커브가 높다. 

     

   3.2 프로젝트에 Spring Data JPA 적용하기

   - `build.gradle`

     > 1. `spring-boot-starter-data-jpa`
     >    - 스프링 부트용 Spring Data JPA 추상화 라이브러리
     >    - 스프링 부트 버전에 맞춰서 자동으로 JPA 관련 라이브러리의 버전을 관리해준다. 
     > 2. `h2`
     >    - 인메모리 관계형 DB
     >    - 별도의 설치 없이 의존성 만으로 관리 가능
     >    - 메모리에서 실행되므로 애플리케이션 재시작마다 초기화됨. 테스트 용도로 많이 사용
     >    - 여기서는 JPA 테스트, 로컬 환경 구동에서 사용할 것임

   - `domain` 패키지 : 게시글, 댓글, 회원, 정산, 결제 등의 소프트웨어 요구사항 영역

     원래는 xml에 쿼리를 답고 클래스에는 쿼리에 결과만 담았지만, 이제는 다 도메인 클래스에서 해결한다.

     [참고 자료(DDD START! 도메인 주도 설계 구현과 핵심 개념 익히기)](http://www.yes24.com/Product/Goods/27750871)

   - 주요 어노테이션을 클래스에 가깝게 두는 게 좋다.

     > `@Entity`는 JPA의 어노테이션이고, `@Getter, @NoArgsConstructor`는 롬복의 어노테이션
     >
     > 롬복은 코드를 단순화 시켜주지만 필수 어노테이션은 아님. 따라서 주요 어노테이션인 `@Entity`를 코드에 가까이 두는 게 좋다. 이 경우 코틀린 등으로 언어를 전환시 롬복이 필요 없는 경우 쉽게 삭제 가능

   - Entity Class

     실제 DB table과 매칭될 클래스, Entity 클래스라고도 함
     * JPA 사용시 DB 데이터에 작업할 경우 실제 쿼리 날리지 않고

     * 이 Entity 클래스의 수정을 통해 작업 가능

   - `com.mingdyuo.book.springboot.domain.posts.Posts`

     > 1. `@Entity`
     >
     >    - 테이블과 링크될 클래스임을 나타냄
     >
     >    - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 매칭
     >
     >      SalesManager.java → sales_manager table
     >
     > 2. `@Id`
     >
     >    - 해당 테이블의 PK 필드를 나타냄
     >
     > 3. `@GeneratedValue`
     >
     >    - PK의 생성 규칙을 나타냄
     >    - 스프링부트 2.0에서는 `GeneratedType.IDENTITY` 옵션을 추가해야만 `auto_increment`가 된다.
     >    - [스프링부트 2.0과 1.5의 차이](https://jojoldu.tistory.com/295)
     >
     > 4. `@Column`
     >
     >    - 테이블의 칼럼을 나타냄
     >
     >    - 굳이 선언 안해도 해당 클래스의 필드는 모두 칼럼이 됨
     >
     >    - 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용한다. 
     >
     >      > (예)
     >      >
     >      > 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나 타입을 TEXT로 변경하고 싶은 경우 등'
     >    
     > 5. `@NoArgsConstructor`
     >
     >    - 기본 생성자 자동 추가
     >    - `public Posts(){ }`와 같은 효과
     >
     > 6. `@Getter`
     >
     >    클래스 내 모든 필드의 Getter 메소드를 자동 생성
     >
     > 7. `@Builder`
     >
     >    - 해당 클래스의 빌더 패턴 클래스를 생성
     >    - 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함

   - Entity의 PK는 Long 타입의 `Auto_increment`가 좋다. (MySQL으로 bigint 타입)

     주민등록번호와 같이 비즈니스 상 unique 키거나, 여러 키를 조합한 복합키로 PK를 잡을 시 난감한 상황이 발생할 수 있음

     1. FK를 맺을 때 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야 하는 상황이 발생할 수 있음
     2. 인덱스에 안좋은 영향
     3. 유니크한 조건 변경시 PK 전체를 수정해야함

     → 주민등록번호, 복합키 등은 유니크 키로 별로 추가하는 것이 좋다.

   - Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다. 

     - 무작정 getter/setter 생성시 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분하기 어려우므로 차후 기능 변경 시 복잡해짐
     - 해당 필드의 값 변경이 필요하다면, 그 목적과 의도를 명확히 나타낼 수 있는 메소드 추가해야함.

   - Setter가 없다면 어떻게 값을 채워서 DB에 삽입할까

     - 기본적 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입하는 것

     - 값 변경이 필요하면 해당 이벤트에 맞는 public 메소드를 호출해서 변경

     - 이 프로젝트에서는 생성자 대신 `@Builder`를 통해 제공되는 빌더 클래스를 사용함

       빌더를 사용하는 경우 어느 필드에 어떤 값을 채워야 하는지 명확히 인지 가능.
     
   - JPA Repository

     - 인터페이스로 생성

   - ibatis나 MyBatis 등에서 Dao라고 불리는 DB Layer임.

     - `JpaRepository<Entity 클래스, PK타입>`상속 시 기본적인 CRUD 메소드가 자동으로 생성됨
     - `@Repository`를 추가할 필요 없으나, Entity 클래스와 기본 Entity Repository는 함께 위치해야함. 둘은 밀접한 관계이며 기본 Repository 없이 Entity 클래스는 역할을 할 수 없음.
     - 프로젝트 규모가 커져서 도메인별로 프로젝트를 분리해야 한다면, 도메인 패키지에서 함께 관리한다.
     

   3.3 Spring Data JPA 테스트 코드 작성하기

   - `com.mingdyuo.book.springboot.web.domain.posts.PostsRepositoryTest`

     > 1. `@After`
     >    - Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정함
     >    - 보통은 배포 전 전체 테스트를 수행할 때 테스트 간에 데이터 침범을 막기 위해 사용함
     >    - 여러 테스트가 동시에 수행되면, 테스트용 DB인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있음.
     > 2. `postsRepository.save`
     >    - 테이블 posts에 `insert/update` 쿼리를 실행
     >    - id 값이 있다면 update가, 없다면 insert 쿼리가 실행됨
     > 3. `postsRepository.findAll`
     >    - 테이블 posts에 있는 모든 데이터를 조회

   3.4 등록/수정/조회 API 만들기

   - API 만들기 위해 필요한 클래스

     1. Request 데이터를 받을 Dto
     2. API 요청을 받을 Controller
     3. 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
        - 얘는 순서만 보장하는 역할을 함
        - 비즈니스 로직을 처리하는 것이 아님

   - Spring 웹 계층

     1. Web layer
     
        - 흔히 사용하는 컨트롤러(`@Controller`)와 JSP/Freemarker 등의 뷰 템플릿 영역
        - 이외에도 필터(`@Filter`), 인터셉터, 컨트롤러 어드바이스(`@ControllerAdvice`) 등 **외부 요청과 응답**에 대한 전반적인 영역을 이야기함.
     
     2. Service layer
     
        - `@Service`에 사용되는 서비스 영역
        - 일반적으로 Controller와 Dao의 중간 영역에서 사용
        - `@Transaction`이 사용되어야 하는 영역이기도 함.
     
     3. Repository layer
        - Database와 같이 데이터 저장소에 접근하는 영역
        - 기존에 개발했다면 Dao(Data Access Object) 영역으로 이해해도 됨
     
     4. Dtos
     
        - Dto는 계층 간에 데이터 교환을 위한 객체를 의미
     
          Dtos는 이들의 영역을 의미
     
        - 예를 들어, 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등이 이를 이야기함.
     
     5. Domain Model
     
        - 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고, 공유할 수 있도록 단순화시킨 것
        
        - 예를 들어 택시 앱을 이야기할 때, 배치, 탑승, 요금 등이 모두 도메인이 될 수 있음
        
        - `@Entity`가 사용된 영역 역시 도메인 모델로 이해할 수 있다.
        
        - 무조건 데이터 베이스의 테이블과 관계가 있어야 하는 것은 아님. VO처럼 값 객체들도 이 영역에 해당하기 때문
       > | Web Layer        | DTOs         |
       > | ---------------- | ------------ |
       > | Service Layer    |              |
       > | Repository Layer | Domain Model |
     
   - 비즈니스 처리를 담당해야 하는 곳은 Domain이다.

   -  기존에 서비스로 처리하던 방식을 트랜잭션 스크립트라고 한다.

   - 스프링에서 Bean을 주입받는 방식

     1. `@Autowired`

        권장하지 않음

     2. setter

     3. 생성자

        가장 권장하는 방식. `@Autowired`와 동일한 효과임

        `@RequiredArgsConstructor`에서 생성자 만들어줌

   - Entity 클래스는 절대 Request/Response로 사용하면 안된다.

     - 데이터 베이스와 맞닿은 핵심 클래스임

     - Entity를 기준으로 테이블이 생성되고 스키마가 변경됨

     - Entity 클래스와 비슷한 내용이지만 request/response용으로 Dto를 따로 만드는 이유임

       → View layer와 DB layer의 철저한 역할 분리를 권장함

     - 실제 controller에서 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 많으므로 Entity 클래스만으로는 표현하기 어려울 수 있음 
     
   - `@WebMvcTest`의 경우 JPA 기능이 작동하지 않으며, Controller와 ControllerAdvice 등 외부 연동과 관련된 부분만 활성화된다. JPA 기능까지 한번에 테스트 할 때에는 `@SpringBootTest`와 같이 TestRestTemplate를 사용함

   - posts update에는 쿼리를 날리는 부분이 없다. 이는 JPA의 영속성 컨텍스트 덕분

   - 영속성 컨텍스트란

     - 엔티티를 영구하게 저장한느 환경, 논리적 개념

     - JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다.

     - JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 DB 데이터를 가져오면, 이 데이터는 영속성 컨텍스트가 유지된 상태임

     - 이 상태에서 해당 데이터의 값을 변경하면, 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다. 즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다. 

       → [더티 체킹](https://jojoldu.tistory.com/415)
       
       

   3.5 JPA Auditing으로 생성시간/수정시간 자동화하기

   - JPA Auditing 사용 이유

     entity에서는 보통 데이터 생성, 수정 시간을 포함하는데, 이 때문에 매번 DB에 삽입 및 갱신 전에 날짜 데이터를 등록하거나 수정하는 코드가 여기저기 쓰이게 된다. 이런 단순 반복을 지양하기 위해 사용

   - LocalData 사용

     날짜 타입 사용. Java8의 `LocalData`, `LocatDateTime`을 쓴다. 

   - `BaseTimeEntity.java`

     1. `@MappedSuperclass`

        JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 함

     2. `@EntityListeners(AuditingEntityListener.class)`

        BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.

     3. `@CreatedDate`

        Entity가 생성되어 저장될 때 시간이 자동 저장됨

     4. `@LastModifiedDate`

        조회한 Entity의 값을 변경할 때 시간이 자동 저장됨

   - 미리 만든 BaseTimeEntity를 상속받으면 추가될 엔티티의 등록/수정일 고민을 할 필요가 없어진다.

   - [자바 ORM 표준 JPA 프로그래밍 - 김영한](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788960777330)
