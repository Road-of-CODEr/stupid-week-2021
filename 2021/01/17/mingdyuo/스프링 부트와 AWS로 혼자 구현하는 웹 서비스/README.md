## 스프링 부트와 AWS로 혼자 구현하는 웹 서비스



4. **머스테치로 화면 구성하기**

   4.1 서버 템플릿 엔진과 머스테치 소개

   - 템플릿 엔진이란

     - 지정된 템플릿 양식과 데이터가 합쳐져 HTML 문서를 출력하는 소프트웨어

     1. 서버 템플릿 엔진 : JSP, Freemarker 등
        - 서버 템플릿 엔진은 서버에서 구동됨
        - 화면 생성을 할 때, 서버에서 Java 코드로 문자열을 만든 뒤 이 문자열을 HTML로 변환하여 브라우저로 전달함
     2. 클라이언트 템플릿 엔진 : 리액트, 뷰 등 
        - 자바 스크립트는 브라우저 위에서 작동됨, 즉 코드의 실행 장소는 서버가 아닌 브라우저, 따라서 서버의 제어를 받을 수 없음
        - 이런 SPA(Single Page Application)는 브라우저에서 화면을 생성함. 
        - 서버에서는 Json, Xml 형식의 데이터만 전달하고 클라이언트에서 조립함
        - 최근데는 리액트나 뷰에서 서버사이드 렌더링을 지원하기도 하는데, 아무튼 그렇다.
        - 스프링 부트를 사용하면서 JS를 서버사이드에서 렌더링하도록 구현하는 것은 많은 수고가 필요함. 

   - [머스테치](http://mustache.github.io)란

     - 수많은 언어를 지원하는 가장 심플한 템플릿 엔진

     - 현존 대부분 언어를 지원 

       → 자바에서 사용 시 서버 템플릿 엔진, JS 사용시 클라이언트 템플릿 엔진으로 사용 가능

     - 장점은 문법이 심플하고, 로직 코드를 사용할 수 없으므로 view 역할과 서버 역할이 명확히 분리되며, *.js와 *.java 둘 다 있으므로 문법 하나로 클라/서버 템플릿 모두 사용 가능하다는 것임.

     - 그 외에 JSP, Velocity, Freemarker, Thymeleaf 등이 있음

       

   4.2 기본 페이지 만들기

   - 머스테치 파일의 기본 위치

     `src/main/resources/templates`

   - 머스테치 스타터는 컨트롤러에서 문자열 반환 시 앞의 경로와 뒤의 파일 확장자를 자동으로 지정함

     앞의 경로는 `src/main/resources/templates`이고 뒤의 파일 확장자는 `.mustache`가 붙는데, 컨트롤러에서 `"index"`를 반환하므로 `src/main/resources/templates.mustache`로 전환 되며 View Resolver가 처리한다.

     (View Resolver란 URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자 격으로 볼 수 ㅣ있다.)

     

   4.3 게시글 등록 화면 만들기

   - 프론트 엔드 라이브러리 사용하는 방법

     1. 외부 CDN을 사용

        간단하므로 이번 프로젝트에서 사용할 것이지만, 외부에 의존하는 것이기 때문에 실제 서비스에서는 잘 사용하지 않는다. 

     2. 직접 라이브러리를 받아서 사용

        npm/bower/yarn + grunt/gulp/webpack 등

   - 레이아웃 방식의 외부 CDN 추가

     공통 영역을 별도의 파일로 분리하여 필요한 곳에서 가져다 쓰는 방식

   - 부트스트랩은 제이쿼리에 의존

   - js 파일들에 같은 이름의 함수가 있다면, 브라우저 내 공용 공간 스코프에 모두 들어가기 때문에 나중에 로딩된 함수가 이전에 로딩된 함수를 덮어쓰게 된다. 이를 해결하기 위해 파일 내에서 유효 범위를 만들어 사용한다.

     (ex) 페이지 별 init(), save() 등

     var 객체를 만들어 해당 객체 내에 필요한 모든 function을 선언하는 방식으로 사용

   - index.js의 호출 경로는 절대경로로 바로 시작한다. 기본적으로 `src/main/resources/static`에 위치한 js, css, image 등의 정적 파일은 url에서 `/`로 설정된다.

   - `index.mustache`

     1. `{{#posts}}`

        - posts라는 List를 순회한다.

        - java의 for문과 동일하게 생각하면 됨

     2. `{{id}}`등의 `{{변수명}}`

        리스트에서 뽑아낸 객체의 필드를 사용

     3. 다음과 같이 사용 가능

        ```
        {{#posts}}
            <tr>
                <td>{{id}}</td>
                <td>{{title}}</td>
                <td>{{author}}</td>
                <td>{{modifiedDate}}</td>
            </tr>
        {{/posts}}
        ```
     
   - 규모가 있는 프로젝트에서는 FK의 조인 등의 복잡한 조건 때문에 entity 클래스만으로 처리하기 어려우므로 조회용 프레임워크를 추가로 사용한다. 대표적으로는 querydsl, jooq, MyBatis 등이 있다. 이 프레임워크들을 이용해서 조회를 하고, SpringDataJpa를 통해 등록/수정/삭제를 진행한다.

   - Querydsl을 개인적으로 추천하는 이유

     1. 타입 안정성이 보장됨

        단순한 문자열로 쿼리를 생성하는 것이 아니라 메소드를 기반으로 쿼리를 생성하므로 오타나 존재하지 않는 컬럼명을 명시할 경우 IDE에서 자동으로 검출됨. 

     2. 국내 많은 회사에서 사용중

        쿠팡, 배민 등 JPA를 적극적으로 사용하는 회사에서는 Querydsl을 적극적으로 사용중임

     3. 레퍼런스가 많음

        많은 회사와 개발자들이 사용하므로 국내 자료가 많음

   - `@Transactional`에 옵션 `readOnly = true`를 추가하면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선됨. 

     등록, 수정, 삭제 기능이 전혀 없는 메소드에서 사용하면 좋다.

   - 람다식

     `.map(PostsListResponseDto::new)`는 `.map(posts -> new PostsListResponseDto(posts))`와 같다.

   - `IndexController.java` 의 Model

     - 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음

     - 여기서는 `postsService.findAllDesc()`로 가져온 결과를 posts로 index.mustache에 전달함

       

   4.5 게시글 수정, 삭제 화면 만들기

   - 에러 발생

     ```
     {"readyState":4,"responseText":"{\"timestamp\":\"2021-01-17T02:56:56.389+0000\",\"status\":405,\"error\":\"Method Not Allowed\",\"message\":\"Request method 'DELETE' not supported\",\"path\":\"/api/v1/posts/1\"}","responseJSON":{"timestamp":"2021-01-17T02:56:56.389+0000","status":405,"error":"Method Not Allowed","message":"Request method 'DELETE' not supported","path":"/api/v1/posts/1"},"status":405,"statusText":"error"
     ```

     에러 발생 이유 : delete mapping을 아직 안한 상태에서 프론트만 만들고 ㅈㄴ 요청 보낸 것이었다..... ㅎㅎ;;

     

5. **스프링 시큐리티와 OAuth 2.0으로 로그인 기능 구현하기**

   5.1 스프링 시큐리티와 스프링 시큐리티 Oauth2 클라이언트

   - 많은 서비스에서 직접 id/password로 로그인 구현보다는 다른 사이트를 경유하는 소셜 로그인 기능을 사용함

     그 이유는 직접 구현하면 배보다 배꼽이 커질 수 가 있기 때문임 (다음과 같은 것)

     > 1. 로그인 시 보안
     > 2. 회원가입 시 이메일 혹은 전화번호 인증
     > 3. 비밀번호 찾기
     > 4. 비밀번호 변경
     > 5. 회원정보 변경

     OAuth 로그인을 구현하면 이런 것들을 다 다른 서버에 맡기면 되니까 서비스 개발에 집중할 수 있음

   - 스프링 부트 1.5 vs 스프링 부트 2.0

     - 연동 방법이 크게 변경되었으나 설정 방법에는 큰 차이가 없다.

       `spring-security-oauth2-autoconfigure` 라이브러리를 사용하기 때문

       기존에 작성했던 코드 사용 가능

     - 킹치만 이 책에서 spring security Oauth2 client 라이브러리를 사용하는 이유

       1. 스프링 팀에서 기존 1.5에서 쓰던 `spring-security-oauth`프로젝트는 유지 상태로 결정함 신규 기능 추가 안하고 버그 수정 정도만 한다고 함. 새로운 기능은 oauth2 새 라이브러리에서만 지원
       2. 스프링 부트용 라이브러리 (starter) 출시됨
       3. 기존에 사용하던 방식은 확장 포인트가 적절하게 오픈되어 있지 않음. 그래서 직접 상속하거나 오버라이딩 해야 함. 신규 라이브러리는 확장 포인트를 고려해서 설계했음

     - 인터넷에서 스프링부트 2 방식의 자료 찾고 싶은 경우 확인해야 할 것

       1. `spring-security-oauth2-autoconfigure` 라이브러리를 썼는지 확인

       2. `application.properties` 혹은 `application.yml` 정보 확인

          > **Spring Boot 1.5**
          >
          > google : 
          >
          > ​	client :
          >
          > ​		clinetId : 인증 정보
          >
          > ​		accessTokenUri: ...	
          >
          > ​		...
          >
          > <u>url 주소를 모두 명시해야 함</u> 
          >
          > **Spring Boot 2.x**
          >
          > spring:
          >
          > ​	security:
          >
          > ​	oauth2:
          >
          > ​		client:
          >
          > ​			...
          >
          > <u>client 인증 정보만 입력해야 함. 직접 입력했던 값들은 enum으로 대체</u>
          >
          > `CommonOAuth2Provider`라는 enum이 새로 추가됨. 구글, 깃허브, 페이스북, 옥타의 기본 설정값은 모두 여기서 제공함

   5.2 구글 서비스 등록

   - 구글 서비스 등록을 할 때에는 인증 정보를 발급받아야 함

   - `application-oauth.properties`

     - `spring.security.oauth2.client.registration.google.scope=profile,email`로 지정해 주었는데, scope를 지정해주지 않으면 기본값이 `openid, profile, email`로 된다. 이 경우 `openid`때문에 Open Id Provider로 인식됨
     - 구글은 OpenId Provider이고 네이버, 카카오는 아니므로 openid를 포함시키면 두 서비스를 나누어서 각각 OAuth2Service를 만들어야 함
     - 준나 귀찮으므로 한번에 하기 위해서 scope를 지정해준다.

   - 스프링 부트에서는 properties의 이름을 `application-xxx.properties`로 만들면 xxx라는 이름의 profile이 생성되어서 이를 통해 관리 할 수 있음

     즉, profile=xxx라는 식으로 호출하면 해당 properties의 설정들을 가져올 수 있다. 
     
     호출 방식에는 여러가지가 있지만 이 책에서는 `application.properties`에서 `application-oauth.properties`를 포함하도록 구성한다.
     
   - 구글 로그인을 위한 클라이언트 ID와 비밀번호는 보안이 중요한 정보이므로 gitignore에 잊지 말고 꼭 추가해줘야 함

   5.3 구글 로그인 연동하기

   - `@Enumerated(EnumType.STRING)`

     - JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정
     - 기본적으로는 int로 된 숫자가 저장되지만, 이러면 DB로 확인할 때 그 값이 무슨 코드를 의미하는 지 알 수 없음
     - 그래서 문자열로 저장하도록 선언한 것

   - 스프링 시큐리티에서는 권한 코드에 항상 `ROLE_`이 앞에 있어야 함

     → `ROLE_GUEST`, `ROLE_USER`

   - `UserRepository`

     `findByEmail` : 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드

   - 의존성 추가 `spring-boot-starter-oauth2-client`

     `spring-boot-starter-oauth2-client`와 `spring-boot-starter-oauth2-jose`를 기본으로 관리해줌

   - `com/mingdyuo/book/springboot/config/auth/SecurityConfig.java`

     1. `@EnableWebSecurity`

        Spring Security 설정 활성화

     2. `csrf().disable().headers().frameOptions().disable()`

        h2-console 화면을 사용하기 위해 해당 옵션을 disable

     3. `authorizeRequests`

        - URL별 권한 관리를 설정하는 옵션의 시작점
        - authorizeRequests가 선언되어야 antMatchers 옵션 사용가능

     4. `antMatchers`

        - 권한 관리 대상을 지정하는 옵션
        - URL, HTTP 메소드별로 관리 가능
        - 여기서 `"/"` 등 지정된 URL들은 `permitAll()`옵션을 통해 전체 열람 권한을 줬음
        - 여기서 `"/api/v1/**"`주소를 가진 API는 USER 권한을 가진 사람만 가능

     5. `anyRequest`

        - 설정된 값들 이외 나머지 URL들을 나타냄
        - 여기서는 `authenticated()`를 추가해서 나머지 URL들은 모두 인증된 사용자에게만 허용하도록 함
        - 인증된 사용자는 로그인된 사용자를 의미

     6. `logout().logoutSuccessUrl("/")`

        - 로그아웃 기능에 대한 여러 설정의 진입점
        - 로그아웃 성공 시 `/` 주소로 이동
        
     7. `oauth2Login`

        OAuth 2 로그인 기능에 대한 여러 설정의 진입점

     8. `userInfoEndpoint`
     
        OAuth 2 로그인 성공 후 사용자 정보를 가져올 때의 설정 담당
     
     9. `userService`
     
        - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록함
        - 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음.
     
   - `com/mingdyuo/book/springboot/config/auth/CustomOAuth2UserService.java`
   
     1. `registrationId`
        - 현재 로그인 진행 중인 서비스를 구분하는 코드
        - 지금은 구글만 사용하는 불필요한 값이지만 이후 네이버 로그인 연동 시에 구분하기 위해 사용
     2. `userNameAttributeName`
        - OAuth2 로그인 진행 시 키가 되는 필드값 의미. Primary Key와 같은 의미임
        - 구글의 경우 기본적으로 "sub"라는 코드를 지원하지만, 네이버와 카카오는 기본으로 지원하지 않음
        - 이후 네이버와 구글 로그인을 동시 지원할 때 사용함
     3. `OAuth Attributes`
        - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        - 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용함
     4. `SessionUser`
        - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        - **왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는지 추후 설명**
   
   - `com/mingdyuo/book/springboot/config/auth/dto/OAuthAttributes.java`
   
     1. `of()`
   
        `OAuth2User`에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다.
   
     2. `toEntity()`
   
        - User 엔티티를 생성
        - OAuthAttributes에서 엔티티 생성 시점은 처음 가입 시점
        - 가입할 때의 기본 권한을 GUEST로 주기 위해서 role 빌더 값에는 `Role.GUEST`를 사용
        - `OAuthAttributes` 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성
   
   - `SessionUser`클래스에는 인증된 사용자 정보만 필요 
   
     User 클래스를 사용하지 않는 이유
   
     - User 클래스는 엔티티이기 때문에 다른 엔티티와 관계가 언제 형성될지 모른다.
   
       따라서 내부에 직렬화를 구현하기가 좀 그렇다
   
     - `@OneToMany`, `@ManyToMany` 등 자식 엔티티를 갖고 있다면, 직렬화 대상에 자식들이 포함된다. 따라서 성능 이슈와 부수 효과가 발생활 확률이 높음
   
     - 그래서 그냥 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 게 운영과 유지보수 측면에서 낫다.
   
   - `src/main/resources/templates/index.mustache`
   
     1. `{{#userName}}`
        - 머스테치는 다른 언어와 같은 if문을 제공하지 않고 true/false 여부만 판단함
        - 따라서 항상 최종값을 넘겨줘야 함
        - userName이 있다면 userName을 노출시키도록 구성한 것
     2. `a href="/logout"`
        - 스프링 시큐리티에서 기본적으로 제공하는 로그아웃 URL
        - 개발자가 별도로 저 URL에 해당하는 컨트롤러를 만들 필요가 없음
        - `SecurityConfig` 클래스에서 URL을 변경할 수는 있지만, 기본 URL을 사용해도 충분하므로 여기서는 그대로 사용
     3. `{{^userName}}`
        - 머스테치에서 해당 값이 존재하지 않는 경우 `^`를 사용함
        - `userName`이 없다면 로그인 버튼을 노출시키도록 구성한 것
     4. `a href="/oauth2/authorization/google"`
        - 스프링 시큐리티에서 기본적으로 제공하는 로그인 URL
        - 로그아웃 URL과 마찬가지로 개발자가 별도의 컨트롤러를 생성할 필요가 없다.
   
   - `src/main/java/com/mingdyuo/book/springboot/web/IndexController.java`
   
     1. `(SessionUser) httpSession.getAttribute("user")`
        - 앞서 작성된 `CustomOAuth2UserService`에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성한 것
        - 즉, 로그인 성공 시 `httpSession.getAttribute("user")`에서 값을 가져올 수 있음
     2. `if(user != null)`
        - 세션에 저장된 값이 있을 때만 model에 userName으로 등록함
        - 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 됨
   
   5.4 어노테이션 기반으로 개선하기
   
   - `src/main/java/com/mingdyuo/book/springboot/config/auth/LoginUser.java`
     1. `@Target(ElementType.PARAMETER)`
        - 이 어노테이션이 생성될 수 있는 위치를 지정
        - `PARAMETER`로 지정했으니, 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
        - 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있음
     2. `@interface`
        - 이 파일을 어노테이션 클래스로 지정
        - LoginUser라는 이름을 가진 어노테이션이 생성되었다고 보면 됨
   - `src/main/java/com/mingdyuo/book/springboot/config/auth/LoginUserArgumentResolver.java`
     1. `supportsParameter()`
        - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
        - 여기에는 파라미터에 `@LoginUser`  어노테이션이 붙어 있고, 파라미터 클래스 타입이 `SessionUser.class`인 경우 `true`를 반환
     2. `resolveArgument()`
        - 파라미터에 전달할 객체를 생성
        - 여기서는 세션에서 객체를 가져옴
   - `src/main/java/com/mingdyuo/book/springboot/web/IndexController.java`
     1. `@LoginUser SessionUser user`
        - 기존에 `(User) httpSession.getAttribute("user")`로 가져오던 세션 정보 값을 개선
        - 어느 컨트롤러든 `@LoginUser`만 사용하면 세션 정보를 가져올 수 있음
   
   5.5 세션 저장소로 데이터베이스 사용하기
   
   - 우리가 만든 서비스는 세션이 내장 톰캣의 메모리에 저장
   
     - 애플리케이션을 재실행하면 로그인이 풀림
     - 세션은 WAS(Web Application Server)의 메모리에서 저장되고 호출됨
     - 애플리케이션 실행 시 실행되는 구조에서는 세션 초기화가 됨
     - 즉, 배포할 때마다 톰캣이 재시작 되는 것
     - 2대 이상의 서버에서 서비스 한다면, 톰캣마다 세션 동기화 설정을 해야 함
   
   - 현업에서 사용하는 세션 저장소
   
     1. 톰캣 세션
   
        - 일반적으로 별다른 설정 하지 않을 시 기본적으로 사용
        - 톰캣(WAS)에 세션이 저장되므로 2대 이상의 WAS가 구동되는 환경에서는 세션 공유를 위한 추가 설정이 필요
   
     2. MySQL과 같은 DB
   
        - 여러 WAS 간의 공용 세션을 사용할 수 있는 가장 쉬운 방법
   
        - 많은 설정이 필요 없으나, 로그인 요청마다 DB IO가 발생
   
          → 성능상 이슈 발생 가능
   
        - 보통 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도에서 사용
   
     3. Redis, Memcached와 같은 메모리 DB
   
        - B2C 서비스에서 가장 많이 사용하는 방식
        - 실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요
   
   - 이 책에서 사용할 세션 저장소는 2번 선택지, 그 이유는
   
     - 설정이 간단함
     - 사용자가 많은 서비스가 아님
     - 비용 절감
   
   - AWS에서 서비스를 배포, 운영할 때를 생각하면 Redis같은 메모리 DB는 별도 사용료를 지불해야 하므로 부담스러움
   
   5.6 네이버 로그인
   
   - `src/main/resources/application-oauth.properties`
   
     1. ` user_name_attribute=response`
        - 기준이 되는 `user_name`의 이름을 네이버에서는 response로 해야 함
        - 왜냐면 네이버의 회원 조회 시 반환되는 JSON 형태 때문
   
   - 스프링 시큐리티에서는 하위 필드를 명시할 수 없고, 최상위 필드만 user_name으로 지정 가능
   
     네이버의 응답값 최상위 필드는 `resultCode, message, response` 세개가 전부이므로 `response`를 `user_name`으로 지정하고 받아오는 것
   
   5.7 기존 테스트에 시큐리티 적용하기
   
   - 기존 테스트에 시큐리티 적용으로 문제가 되는 부분
   
     - 기존에는 바로 API를 호출할 수 있으므로, 테스트 코드 역시 바로 API를 호출하도록 구성함
   
       하지만 시큐리티 옵션이 활성화 되면 인증된 사용자만 API를 호출할 수 있음
   
     - 기존 API 테스트 코드들이 모두 인증에 대한 권한을 받지 못하였으므로, 테스트 코드마다 인증한 사용자가 호출한 것처럼 작동하도록 수정해야 함
   
     - `src/main `환경과 `src/test` 환경은 각자만의 환경 구성을 가짐. `test`에 `application.properties`가 없으면 자동으로 가져오는데, 자동으로 가져오는 옵션의 범위는 저것 까지임. `application-oauth.properties`는 자동으로 가져오지 않음. 
   
     - 따라서 테스트 환경을 위해서 `application.properties`를 만들어줘야 한다. 이 때에는 가짜 설정값을 등록한다.
   
   - `src/test/java/com/mingdyuo/book/springboot/web/PostsApiControllerTest.java`
   
     1. `@WithMockUser(roles="USER")`
   
        - 인증된 모의 사용자를 만들어서 사용
   - roles에 권한 추가 가능
        - `ROLE_USER`권한을 가진 사용자가 API를 요청하는 것과 동일한 효과
   
     2. `@Before`
   
        매번 테스트가 시작되기 전에 `MockMvc` 인스턴스를 생성
     
     3. `mvc.perform`
     
        - 생성된 MockMvc를 통해 API를 테스트
        - 본문 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
     
   - `@WebMvcTest`는 CustomOAuth2UserService를 스캔하지 않음
   
     - `@ControllerAdvice`, `@Controller`는 읽는다.
     - `@Repository`, `@Service`, `@Component`는 스캔하지 않는다.
   
   - `@EnableJpaAuditing`을 사용하기 위해서는 최소 하나의 `@Entity` 클래스가 필요함
   
     `@WebMvcTest`에는 없음
   
   - `@EnableJpaAuditing`는 `@SpringBootApplication`과 함께 있으므로 `@WebMvcTest`에서도 스캔함. → 둘을 분리해야함



ㅎ....하... 따라 가고는 있는데 정보량이 방대해서 점점 이해가 잘 안된다 일단 멱살잡고 끝까지 끌려 가봐야지,,,