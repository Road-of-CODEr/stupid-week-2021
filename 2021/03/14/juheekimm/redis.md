강의 링크 : [우아한테크세미나](https://www.youtube.com/watch?v=mPB2CZiAkKM)

## 캐시란?

나중에 요청된 결과를 미리 저장해두었다가 빨리 제공하기 위해 사용한다.

Dynamic Programming의 핵심은 '앞의 연산 결과를 미리 저장해 놓고 다음번에는 똑같은 연산을 하지 말자'가 포인트

Factorial 개념과도 유사한데, 9999! 를 계산 하고자 할 때 9998! 까지를 미리 계산해두고 어딘가에 저장해두었다면 그 다음부터 9999! 의 계산은 금방 처리할 수 있는 것과 같은 목적이다.

즉, 9998! 결과를 저장해두는 것과 아닌 것은 접근 속도 부터가 다르기 때문에 캐시를 사용하고 있다.

![image](https://user-images.githubusercontent.com/26156701/111077341-b6b3e000-8533-11eb-9242-58c511d89e02.png)

용량은 위로갈수록 커지고, 속도는 내려갈수록 빨라진다.

Disk가 제일 느리고, CPU가 가장 빠르다.

보통 Redis Cache 는 메모리 단 (In-Memory) 에 위치한다.

따라서 디스크보다 수용력(용량) 은 적지만 접근 속도는 빠르다.

![image](https://user-images.githubusercontent.com/26156701/111077347-c03d4800-8533-11eb-8780-a5f6b571207d.png)

cache 철학에는 파레토 법칙이 있다.

파레토 법칙이란 80퍼센트의 결과는 20퍼센트의 원인으로 인해 발생한다는 뜻

모든 결과를 캐싱할 필요는 없으며, 서비스를 할 때 많이 사용되는 20%를 캐싱한다면 전체적으로 영향을 주어 효율을 극대화 할 수 있다는 의미이다.

### Look aside cache (Lazy Loading)

![image](https://user-images.githubusercontent.com/26156701/111077351-c3d0cf00-8533-11eb-8d63-ea53489c2e29.png)

1. Cache에 Data 존재 유무 확인
1. Data가 있다면 cache의 Data 사용
1. Data가 없다면 DB Data 사용
1. DB에서 가져온 Data를 Cache에 저장

look aside cache는 캐시를 한번 접근하여 데이터가 있는지 판단한 후, 있다면 cache의 데이터를 사용하며 없다면 실제 DB 또는 API를 호출하는 로직으로 구현한다. 대부분의 cache를 사용한 개발이 해당 프로세스를 따른다.

### Write Back

![image](https://user-images.githubusercontent.com/26156701/111077355-c7fcec80-8533-11eb-9661-be8386df4ba1.png)

write back은 cache를 다르게 이용하는 방법

데이터를 db에 저장하지 않으면 데이터가 날아가는데, 캐시에 미리 저장해놓고 특정 시점마다 db에 전달하는 방식이다.

배치의 경우 이 방식을 사용하는데 엄청나게 속도 차이가 난다.

<b>DB는 접근 횟수가 적을수록 전체 시스템의 퍼포먼스는 좋아진다.</b>

데이터를 쓰거나 많은 데이터를 읽게되면 DB에서 Disk에 접근하게 되는데 이렇게 되면 Application의 속도 저하가 일어날 수 있다.

따라서 write back은 데이터를 cache에 모으고 일정한 주기 또는 일정한 크기가 되면 한번에 처리한다.

Log를 많이 저장해야 하거나, 극도로 큰 write 작업이 필요한 경우 redis같은 캐시에 넣고 한번에 db에 저장할 때 사용한다.

> 단점
>
>처음에 cache에 먼저 저장하는데 cache는 memory이기 때문에 Rebooting되거나 서비스에 장애가 생길 경우 데이터가 사라질 위험성이 있다.

## Redis 특징

Redis와 타 캐시 시스템(ex. MemCache 등)과의 차이

### 1. Redis는 List, Set, Sorted Set, Hash 등과 같은 Collection을 지원한다.

처음 개발할 때는 이미 구현된 내용을 가져다 쓰는것이 편하다.

외부의 컬렉션을 잘 사용하면 여러 개발시간을 단축시키고 문제를 줄여줄수 있기 때문에 Collection이 중요.

#### ex1

C와 파이썬중 파이썬이 생산성이 높다고 하는 이유는 파이썬이 제공하는 라이브러리가 훨씬 다양하고 많기 때문이다.

멤캐시드를 쓰면 그 라이브러리를 직접 구현해서 쓰는 그런 느낌인 것.

#### ex2

랭킹 서비스를 어떻게 구현할것인가?

- 가장 간단한 방법? : DB에 유저의 Score 저장후 order by로 정렬하여 읽기

- 데이터가 많아지면 결국 디스크를 사용하기 때문에 속도가 더뎌지는 문제 발생 가능. -> 따라서 인메모리기준 랭킹 서버의 구현이 필요함
    
    - ->Redis의 Sorted Set을 이용하면 랭킹 구현 가능.

### 2. Race condition에 빠질 수 있는 것을 방지한다.

- Redis는 Single Thread

- 따라서 Atomic 보장

#### ex1

친구 리스트를 key/value 형태로 저장해야 한다면?

현재 유저 user가 있고, 친구 KEY는 friends:user 이다. 현재 친구 A가 있다고 가정한다.

동시에 친구추가가 일어났을때 문제 발생 가능

T1: friends:user 읽음 -> 친구 B추가 -> friends:user 쓰기

T2: friends:user 읽음 -> 친구 C추가 -> friends:user 쓰기

결과가 A,B,C나 A,C,B가 되어야하는데 하나가 덮어써져서 유실될 수 있다. (A,B / A,C)

<u>-> Redis 경우 자료구조가 Atomic 하기 때문에 해당 Race Condition을 피할 수 있다!</u> 

(물론 잘못 짜면 발생할 가능성은 존재한다.)

### 3. persistence를 지원하여 서버가 꺼지더라도 다시 데이터를 불러들일 수 있다.

## Redis의 사용

Remote Data Store : A,B,C.. 여러 서버에서 데이터를 공유하고 싶을때

- 한 서버에서만 필요하다면 전역변수 쓰면되지 않나? : Redis는 Atomic하기 때문에 thread safe하기도 하고, 애초에 single thread임

주로 많이 쓰이는 곳들

- 인증토큰 등 저장 (String or Hash)
- Ranking 보드로 사용 (Sorted Set)
- 유저 API Limit
- 좌표 (List)

## Redis Collections

자료구조 선택에 신중해야 한다. 잘못 선택하면 과도하게 속도가 느려지거나 효율이 떨어질 수 있다. 

key-value(String)과 Sorted set을 많이 쓴다

### Strings (key-value로 저장하는 방식)

- 단일 key
    - Set : \<key\> \<value\>
    - Get : \<key\> \<value\>
- 멀티 key
    - mset \<key1\> \<value1> \<key2\>\<value2\> ...
    - mget \<key1\> \<key2\> .....
- key를 어떻게 잡느냐에따라 분산이 달라질수 있다.

### List (head, tail은 빠르지만 중간에 데이터를 삽입하려면 느림)

- 기본 사용법 (insert)
    
    - Lpush \<key\> \<A\>

        -> Key : (A)
    - Rpush \<key\> \<B\>

        -> Key : (A,B)

    - Lpush \<key\> \<C\>

        -> Key : (C,A,B)

    - Rpush \<key\> \<D,A\>

        -> Key : (C,A,B,D,A)

- 기본 사용법 (pop)

    - Key : (C,A,B,D,A)

    - LPOP \<key\>

        -> POP : C, key: (A,B,D,A)

    - RPOP \<key\>

        -> POP : A, key: (A,B,D)
- blpop, brpop

    - 데이터가 없는 경우 누가 데이터를 push하기 전까지 계속 대기

### Set (중복 x, 탐색이 빠르다)

- 기본 사용법

    - SADD \<Key\> \<value\>

        -> 해당 key에 대한 value가 이미 존재하는 경우 추가되지 않는다.

    - SMEMBERS \<key\>

        -> 모든 value를 돌려줌

        - 개수가 많을 경우 엄청나게 느려질 수 있다

        - 팔로우리스트, 친구리스트 등에 사용

    - SISMEMBER \<Key\>\<value\>

        -> value가 존재하면 1, 없으면 0을 리턴

### Sorted Set (Score를 줘서 순서를 보장하는 set)

- 기본 사용법

    - ZADD \<key\> \<score\>\<value\>

        -> Key에 대한 value가 이미 존재하면 Score로 변경된다.

    - ZRANGE \<Key\> \<StartIndex\>

        -> 해당 index 범위 값을 모두 돌려줌 ex) Zrange testkey 0-1 -> (0부터 -1) : 모든 범위를 가져옴

        - 0~2면 0, 1만 가져옴), 인덱스임, 우측은 하나 빼기해서 가져온다.

- 특이사항

    - score값으로 정렬되어서 저장되는데(오름차순), double형이라서 실수하기쉽다.

    - js에서 통신해야한다면 문자열을 사용해아한다.

    - 특정 정수는 실수에서 표현되지 않는 문제가 발생. 순서가 틀어져서 나올 수 있다.

### hash : Key 밑에 sub key로 해서 존재하는 형태

- 기본 사용법

    - Hmset \<key\> \<subkey1\>\<value1\>\<subkey2\>\<value2\>

    - Hgetall \<key\>

        -> 해당 key의 모든 subKey,value 를 가져옴

    - Hget \<key\> \<subkey\>

    - Hmget \<key\>\<subkey1\>\<subkey2\>......

- 특이사항 : 하나의 컬랙션에 너무 많은 아이템을 담으면 좋지 않음

    => 10000개 이하 몇천개 수준으로 유지하는 것이 좋음

- Expire는 collection의 각 item에 개별적으로 걸리지 않고 전체 Collection에 대해서만 걸림. 일부에만 걸 수는 없다.

    -> 즉 해당 10000개에 아이템을 가진 collection이 expire이 설정되어 있다면 해당 시간 이후에 10000개의 아이템이 모두 삭제됨

## Redis 운영

### 메모리 관리를 잘하자

- Redis는 In-Memory Data Store → 메모리 관리가 진짜 중요하다! 메모리가 꽉 차면 죽을 수도 있다.

    - 메모리를 적게쓸때는 성능이 좋지만 많이 쓰면 오히려 역효과

    - 레디스는 in-memory라서 빠른것, 디스크를 쓰게 되는 순간부터 느려진다.

- in-memory data store이기 때문에 Physical Memory를 사용할 경우 문제가 발생할수 있음. 바로 죽지는 않는다.

    -> swap이 있다면 swap 사용으로 해당 메모리 page 접근시 마다 latency가 발생 : 한번이라도 swap이 발생한 메모리 페이지는 계속해서 swap이 일어난다.

    -> swap이 없다면 O(n)의 경우 잘못되기 쉽다.

- Max memory (정해진 메모리 이상은 쓰지 못하게 설정값을 주는 것. 더 써야하면 레디스가 키를 랜덤하게 지우거나 expire목록에 있는 것들을 지움)를 설정하더라도 이보다 더 사용할 가능성이 크다.

    - redis는 memory pool을 쓰는게 아니라 jemalloc 같은 memory allocator에 의존함. 그래서 jemalloc의 구현에 따라 성능이 달려있고, jemalloc은 지웠다고 하지만 실제로는 안 지운 경우 등등 문제 발생 소지가 있다.

    - jemalloc을 쓰기때문에 정확하게 얼마나 데이터를 사용했는지 redis는 파악하지 못한다.

- Rss값을 주기적으로 모니터링 해야함

- 많은 업체가 현재 메모리를 사용해서 swap을 하고 있다는 것을 모르는 경우가 많음

- 큰 메모리를 사용하는 한개의 instance 보다는 적은 메모리를 사용하는 여러 개의 instance가 안전함

    - 레디스는 필연적으로 fork를 하게 되어 있는데, write가 많은 경우 최대 기본 메모리의 2배까지 쓸 수 있다. 문제를 야기할 확률이 높음.

    - 여러 instance를 관리하긴 귀찮지만 이렇게 쓰는 것이 더 안정적이다.

- Redis는 메모리 파편화가 발생할수 있음

    -> 4.xx 부터 메모리 파편화를 줄이도록 jemalloc에 힌트를 주는 기능이 추가 되었으나 jemalloc 버전에 따라서 다르게 동작할수 있음

    -> 3.xx 인경우 실제 사용되는 메모리는 2GB로 보고 되지만 11G의 RSS를 사용하는 경우가 자주 발생

    - 다양한 사이즈를 가지는 데이터 보다는 유사한 크기의 데이터를 가지는 경우가 유리하다.

- 메모리가 부족한 경우 다음과 같이 조치 할 수 있음

    - Cache in Cash !!!

        -> 좀더 메모리 많은 장비로 Migration

        -> 메모리가 빡빡하면 Migration 중에 문제가 발생할수도 있다. 가용 메모리의 60-70% 정도를 사용한다면 Migration을 고려해 볼 것.

    - 기존에 있는 데이터 줄이기

        -> 데이터를 일정 수준에서만 사용하도록 특정 데이터를 줄임

        -> 다만 이미 swap을 사용 중이라면 프로세스를 재시작 해야함

    - 기본적으로 Collection 들은 다음과 같은 자료 구조를 사용

        - Hash -> hashTable을 이용

        - Sorted set -> Skiplist, HashTable을 이용 (값으로도, 인덱스로도 찾아야 하니까)

        - set -> HashTable 이용

        - 해당 자료구조들은 기본적으로 메모리를 많이 사용함

            -> Ziplist를 이용할 것 (단 속도는 조금 느려짐. 그러나 메모리는 훨씬 적게 쓴다).

            - 외부에서는 기존의 자료구조를 사용하고, 내부적으로 Ziplist를 쓰도록 설정만 바꿔줄 수 있다.

            - ziplist를 쓸 수 있는 이유는, 인메모리 특성상 적은 개수라면 선형 탐색을 하더라도 빠르기 때문이다.

            - list, hash, sorted set등을 ziplist로 대체해서 처리하는 설정이 존재한다

            - 많이 넣으면 원래의 자료구조로 바뀌기 때문에 속도는 속도대로 쓰고 메모리도 많이 쓰게될 수도 있으므로 유의할 것.

 
### O(N) 관련 명령어는 주의하자

- Redis 는 Single Threaded 임 (Redis Server는 multi Thread)

    - Q.그러면 Redis는 동시에 여러 개의 명령을 처리할수 있을까? → A.동시에 처리할 수 있는 명령은 한번에 하나임.

    - 참고로 단순한 get/set의 경우 초당 10만 tps 이상 가능 (cpu 속도에 영향을 받음)

        - 근데 하나가 1초걸리는걸 하면 99999개는 기다려야 하는 문제가 있다.

    - Packet으로 하나의 Command가 완성되면 process Command에서 실제로 실행됨

        - 그래서 이 하나가 루프를 탈출해야만 다음 루프를 탈 수 있음.

        - 짧은건 괜찮지만 하나라도 길어지면 문제가 생긴다.

    - 한번에 하나의 명령어를 수행하기 때문에, 긴 시간이 걸리는 명령어를 사용하면 안된다.

- 대표적인 O(N) 명령어

    - Keys : 모든 키를 순회 (몇십만개 넘어가면 Redis 때문에 exception 발생)

    - FLUSHALL , FLUSHDB

    - Delete Collections : 백만개짜리 데이터를 지우면 그 동안은 아무것도 못함

    - Get All Collections

- 대표적인 사례

    - key가 백만개 이상인데 확인을 위해 Keys 명령어를 사용하는 경우

        -> 모니터링 스크립트가 1초에 한번씩 Key를 호출하는 최악의 상황 발생

    - 아이템이 몇만개가 든 hash, sorted set, set에서 모든 데이터를 가져오는 경우

        -> 예전의 Spring security oauth Redis TokenStore

- keys 는 어떻게 대체할 것인가?

    - Scan 명령을 사용하는 것으로 하나의 긴 명령을 짧은 여러번의 명령으로 대체 가능

    - keys 명령어 사이사이에도 빠르게 다른 명령어가  실행됨.

- Collection의 모든 item들을 가져와야 할 때?

    - Collection의 일부만 가져 오거나

    - 큰 Collection을 작은 여러개의 Collection으로 나누어 저장

        -> ex) Userranks -> Userrank1, Userrank2, Userrank3

        -> 하나당 몇천개 안쪽으로 저장하는게 좋음

- Spring security oauth RedisTokenStore 이슈

    - Access Token의 저장을 List(O(N)) 자료구조를 통해서 이루어짐

    - 검색, 삭제시에 모든 item을 매번 찾아봐야 함

- 검색, 삭제시에 모든 item을 매번 찾아봐야 함

    -> 100만개쯤 되면 전체 성능에 영향을 미침

    -> 현재는 Set(0(1))을 이용해서 검색, 삭제를 하도록 수정되어 있음. 이제 갯수가 많아져도 성능 차이가 없음. 

### Replication

- 기본적으로 master / slave 구조

- Async Replication

    -> Replication Lag가 발생할수 있음

    - a에 있는 데이터가 바뀌면 b에 있는 데이터에 바꾸라고 쏴 주는데 그 틈 사이에는 a의 데이터와 b의 데이터가 다를 수 있다. 이런 경우 때문에 발생 가능

    - master에는 데이터가 있고 slave에는 없는 경우가 있다.

    - 이 Lag이 많이 벌어지면 slave가 연결을 끊어버리고 다시 master와 연결하는데 그 시간동안 부하가 늘어난다.

- 'Replicaof' 명령어 이용 가능 (5.x 이상) or 'slaveof' 명령으로 설정 가능

    - 5.0부터 replica와 original로 용어가 바뀜

- DBMS으로 보면 statement replication이랑 유사

- Replication 설정 과정

    - Secondary에 replicaof or slaveof 명령 전달

    - Secondary는 Primary에 sync 명령을 전달

    - Primary는 현재 메모리 상태를 저장하기 위해 fork ← fork는 정말 최악이지만 아직은 고칠 수 없다.

    - Fork 한 프로세스는 현재 메모리 정보를 disk에 dump

    - 해당 정보를 Secondary에 전달

    - Fork  이후의 데이터를 secondary에 계속 전달

- 주의점

    - Replication 과정에서 fork 가 발생하므로 메모리 부족이 발생할수 있음

    - Redis-cil --rab 명령은 현재 상태의 메모리 스냅샷을 가져오므로 같은 문제를 발생시킴

    - AWS 나 클라우드 Redis 는 좀 더 다르게 구현되어서 좀 더 해당 부분이 안정적임

    - 많은 대수의 Redis 서버가 Replica를 두고 있다면

        - 네트워크 이슈, 사람의 작업으로 인해 동시에 Replication이 재시도 되도록 하면 문제 발생

        - ex) 같은 네트워크 30GB를 사용하는 Redis master 100대 정도가 리플리케이션을 동시에 재시작하는 경우 어떤 문제가 발생할까?

- 권장 설정 TIP!!!!

    - MaxClient 설정 50000 (이만큼만 네트워크로 접속할 수 있다. 저 값을 넘으면 접속이 안되니까 크게 잡기)

    - RDB/AOF 설정 off -> 성능 / 안정성이 높아짐

        - 마스터는 둘다 끄고, 필요한 경우 replica에서만 만 RDB/AOF를 사용한다.

    - 특정 commands disable (Keys 등. 얘는 만드시 disable 하는게 좋다.)

        -> AWS의 ElasticCache는 이미 하고 있음

    - 전체 장애의 90% 이상이 keys와 save 설정을 사용해서 발생

        -> 적절한 Ziplist 설정 필요

    - Redis 데이터 분산

        - 데이터의 특성에 따라서 선택할수 있는 방법이 달라짐