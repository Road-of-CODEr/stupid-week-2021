## 스프링 부트와 AWS로 혼자 구현하는 웹 서비스



6. **AWS 서버 환경을 만들어보자 - AWS EC2**

   24시간 작동하는 서버 환경을 만드려면

   > 1. 집에 있는 PC를 24시간 구동
   >
   >    - 일반적으로 비용이 저렴
   >
   > 2. 호스팅 서비스(cafe24, 코리아호스팅 등)을 이용
   >
   >    - 일반적으로 비용이 저렴
   >
   > 3. 클라우드 서비스(AWS, AZURE, GCP 등)을 이용
   >
   >    - 유동적으로 사양을 늘릴 수 있음, 특정 시간에만 트래픽이 몰림
   >
   >    - 단순히 물리 장비만 대여하는 것이 아님
   >
   >      서버 내부의 로그 관리, 모니터링, 하드웨어 교체, 네트워크 관리 등을 지원

   클라우드의 형태

   > 1. Infrastructure as a Service (IaaS, 아이아스, 이에스)
   >    - 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스
   >    - 가상 머신, 스토리지, 네트워크, 운영체제 등의 IT 인프라를 대여해 주는 서비스
   >    - AWS의 EC2, S3 등
   > 2. Platform as a Service (PaaS, 파스)
   >    - 앞에서 언급한 IaaS에서 한번 더 추상화한 서비스
   >    - 따라서 많은 기능이 자동화되어 있음
   >    - AWS의 Beanstalk(빈스톡), Heroku(헤로쿠) 등
   > 3. Software as a Service (SaaS, 사스)
   >    - 소프트웨어 서비스
   >    - 구글 드라이브, 드랍박스, 와탭 등

   6.1 AWS 회원 가입

   6.2 EC2 인스턴스 생성하기

   - EIP 할당
     - 인스턴스 중지하고 다시 시작할 때마다 IP가 새로 할당된다. 이렇게 되면 매번 접속해야 하는 IP 바뀌어서 매우 번거로우므로 고정 IP를 할당한다.
     - AWS에서 고정 IP를 Elastic IP(EIP, 탄력적 IP)라고 한다.

   6.3 EC2 서버에 접속하기

   6.4 아마존 리눅스 1 서버 생성 시 꼭 해야 할 설정들

   - 필수로 해야 하는 설정

     1. Java 8 설치

     2. 타임존 변경

        기본 서버의 시간은 미국 시간대임. 한국 시간대가 되어야 우리가 사용하는 시간이 모두 한국 시간으로 등록되고 사용됨. 

        ```
        sudo rm /etc/localtime
        sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
        ```

     3. 호스트네임 변경

        현재 접속한 서버의 별명을 등록함. 실무에서는 수십 대의 서버가 작동됨. IP만으로는 서버의 각 역할을 알 수 없으므로 호스트 네임을 필수로 등록한다.

        `sudo vim /etc/sysconfig/network`에서 `HOSTNAME=원하는 호스트네임`을 입력하고 저장한다.

        [호스트 네임 미등록시 발생할 수 있는 이슈](https://woowabros.github.io/experience/2017/01/20/billing-event.html)

7. AWS에 데이터베이스 환경을 만들어보자 - AWS RDS

   - 웹 백엔드에서 애플리케이션 코드만큼 데이터베이스 다루는 것이 중요하다.
   - 어느 정도의 DB 구축, 쿼리 튜닝에 대한 기본적 지식이 필요함
   - 이 책에서는 직접 DB를 설치하지는 않고, AWS에서 지원하는 관리형 서비스인 RDS(Relational Database Service)를 이용한다.

   7.1 RDS 인스턴스 생성하기

   - MariaDB 추천 이유

     1. 가격이 저렴함

     2. Amazon Aurora 교체가 용이함

        Aurora는 AWS에서 MySQL과 PostGreSQL을 클라우드 기반에 맞게 재구성한 데이터 베이스임. 호환이 잘되고 발전하고 있음. 추후에 이 Aurora로 바꾸기가 좋다. (Aurora는 비싸기 때문에 이걸로 시작하지는 않음)

   7.2 RDS 운영환경에 맞는 파라미터 설정하기

   - RDS 생성 시 해야 하는 설정
     1. 타임존
     2. Character Set
     3. Max Connection

   7.3 내 PC에서 RDS에 접속해보기

   - 보안 그룹에 IP와 EC2 추가하면 EC2와 RDS간에 접근 가능
   - EC2는 2, 3대가 될 수도 있는데, 매번 IP를 등록할 수는 없으니 보편적으로 보안 그룹 간에 연동을 진행함
   - RDS 엔드 포인트 : 접근 가능한 URL

   7.4 EC2에서 RDS에서 접근 확인

   

8. EC2서버에 프로젝트를 배포해 보자

   8.1 EC2에 프로젝트 Clone 받기

   8.2 배포 스크립트 만들기

   - 배포의 의미
     1. git clone 혹은 git pull을 통해서 새 버전의 프로젝트를 받음
     2. Gradle이나 Maven을 통해 프로젝트 테스트와 빌드
     3. EC2 서버에서 해당 프로젝트 실행 및 재실행
   - 쉘 스크립트
     - 배포할 때마다 하나하나 명령어를 실행할 필요 없도록 해주는 프로그램 파일
     - `.sh`확장자 가짐
   - `~/app/step1/deploy.sh`
     1. `REPOSITORY=/homeec2-user/app/step1`
        - 프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하는 값이므로 변수로 저장
        - `PROJECT_NAME=springboot2-webservice`도 동일하게 변수로 저장
        - 쉘에서는 타입 없이 선언 및 저장
        - 쉘에서는 `$변수명`으로 변수를 사용할 수 있음
     2. `cd $REPOSITORY/$PROJECT_NAME/`
        - 제일 처음 `git clone` 받았던 디렉토리로 이동
        - 바로 위의 쉘 변수 설명을 따라 `/home/ec2-user/app/step1/springboot2-webservice` 주소로 이동
     3. `git pull`
        - 디렉토리 이동 후, master branch의 최신 내용을 받음
     4. `./gradlew build`
        - 프로젝트 내부의 gradlew로 build를 수행
     5. `cp ./build/libs/*jar $REPOSITORY/`
        - build의 결과물인 jar 파일을 복사해 jar 파일을 모아둔 위치로 복사
     6. `CURRENT_PID=$(pgrep -f springboot_webservice)`
        - 기존에 수행 중이던 스프링 부트 애플리케이션을 종료
        - pgrep은 process id만 추출하는 명령어
        - `-f` 옵션은 프로세스 이름으로 찾는 것
     7. `if ~ else ~ fi`
        - 현재 구동중인 프로세스가 있는지 없는지 판단
        - process id 값을 보고 프로세스가 있으면 종료
     8. `JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)`
        - 새로 실행할 jar 파일명을 찾음
        - 여러 jar 파일이 생기기 때문에 `tail -n`로 가장 나중의 jar 파일(최신 파일)을 변수에 저장
     9. `nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &`
        - 찾은 jar 파일명으로 해당 jar 파일을 nohup으로 실행
        - 스프링 부트의 장점으로, 외장 톰캣을 설치할 필요가 없음
        - 내장 톰캣을 사용해서 jar 파일만 있으면 바로 웹 애플리케이션 서버 실행 가능
        - 일반적으로 자바 실행 시 `java -jar`라는 명령어를 사용하지만, 이렇게 실행하면 사용자가 터미널 접속을 끊을 때 애플리케이션도 같이 종료됨
        - `nohup` 명령어 사용 시 애플리케이션 실행자가 터미널을 종료해도 애플리케이션이 계속 구동

   8.3 외부 Security 파일 등록하기

   - `~/app/step1/deploy.sh`
     1. `-Dspring.config.location`
        - 스프링 설정 파일 위치를 지정
        - 기본 옵션들을 담고 있는 `application.properties`와 OAuth 설정들을 담고 있는 `application-oauth.properties`의 위치를 지정
        - `classpath`가 붙으면 jar 안에 있는 `resources` 디렉토리를 기준으로 경로 생성
        - `application-oauth.properties`는 외부에 파일이 있기 때문에 절대 경로를 사용함 

   8.4 스프링부트 프로젝트로 RDS 접근하기

   - MariaDB에서 스프링부트 프로젝트를 실행하기 위해 필요한 작업
     1. RDS 테이블 생성
        - H2에서는 테이블을 자동 생성해주었음
        - MariaDB에서는 직접 쿼리를 이용해 생성해야 함
        - 여기에서는 JPA가 사용될 엔티티 테이블과 스프링 세션이 사용될 2종류 테이블을 생성
        - JPA가 사용할 테이블은 테스트 코드 수행시 로그로 생성되는 쿼리를 사용하면 됨
     2. 프로젝트 설정
        - 자바 프로젝트가 MariaDB에 접근하려면 데이터베이스 드라이버가 필요
        - MariaDB에서 사용 가능한 드라이버를 프로젝트에 추가
     3. EC2 (리눅스서버) 설정
        - DB의 접속 정보는 중요하게 보호해야 할 정보임
        - 공개되면 외부에서 데이터를 모두 가져갈 수 있음
        - 프로젝트 내에 접속정보가 있다면, 해킹 위험이 있으므로 EC2 서버 내부에서 접속 정보를 관리하도록 설정해야 함
   - `application-real.properties`로 파일 만들면 실제 운영할 `profile=real`인 환경이 구성된다고 보면 된다. 보안/로그 상 이슈가 될 만할 설정을 모두 제거하는 RDS 환경 profile을 설정한다. 
   - `application-real-db.properties`
     1. `spring.jpa.hibernate.ddl-auto=none`
        - JPA로 테이블이 자동 생성되는 옵션을 None으로 지정 → 자동으로 생성하지 않음
        - RDS에는 실제 운영으로 사용될 테이블이므로 절대 스프링부트에서 새로 만들지 않도록 한다.
        - 이 옵션을 하지 않으면 자칫 테이블이 모두 새로 생성될 수 있음
        - 주의해야 하는 옵션임
   - `deploy.sh`
     1. `-Dspring.profiles.active=real`
        - `application-real.properties`를 활성화
        - `application-real.properties`의 `spring.profiles.include=oauth,real-db` 옵션 때문에 `real-db` 역시 활성화 대상에 포함됨
   
   8.5 EC2에서 소셜 로그인하기
   
   - AWS 보안 그룹 변경
   - AWS EC2 도메인으로 접속
   - 구글에 EC2 주소 등록

