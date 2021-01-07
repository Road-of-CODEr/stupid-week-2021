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
