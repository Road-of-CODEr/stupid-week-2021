## 데이터베이스 공부

#### 쿼리 구문 속성 맛보기 강의 : [SELECT ALL FROM SQL](https://www.inflearn.com/course/sql-select#)

##### SELECT

- 줘라

- ````sql
  SELECT 열이름1, 열이름2 FROM 테이블명;
  ````

  테이블명에서 열을 **가지고 와라**

- 옵션

  1. 숫자 제한 (LIMIT)

     `SELECT CustomerID FROM Customers LIMIT 5;` 상위 다섯개

  2. 정렬 (ORDER BY)

     `SELECT CustomerID FROM Customers ORDER BY CustomerID DESC;`

     `SELECT CustomerID FROM Customers ORDER BY CustomerID ASC;`

  3. 중복 제거 (DISTINCT)

     `SELECT DISTINCT County FROM Customers`

##### WHERE

- 조건에 맞는 걸로

- ````sql
  SELECT 열이름 FROM 테이블명 WHERE 조건; 
  ````

  테이블 명에서 열을 가지고 오는데, **조건에 맞는 것만** 갖고 와라

- 옵션

  1. 논리 연산자 (=, !=, >=, >, <=, <)

     `SELECT ProductID, Price FROM Products WHERE Price >= 15;`

  2. 목록 포함 (IN, NOT IN)

     `SELECT ProductID, SupplierID FROM Products WHERE SupplierID IN ('1', '2');`

     `SELECT ProductID, SupplierID FROM Products WHERE SupplierID NOT IN ('1', '2');`

  3. 문자열 포함 (LIKE)

     `SELECT ProductID, Unit FROM Products WHERE Unit LIKE '%boxes%';`

     `%`는 앞뒤에 문자열이 더 붙어있어도 되는지 의미

  4. AND, OR

     `SELECT ProductID, Unit, Price FROM Products WHERE Unit LIKE '%boxes%' AND Price >= 15;`

##### GROUP BY

- 묶어서

- ```sql
  SELECT 열이름, 통계함수 FROM 테이블명 GROUP BY 열이름
  ```

  ```sql
  SELECT Country, COUNT(CustomerID) FROM Customers GROUP BY Country
  ```

- 옵션

  1. Having

     통계함수를 통해 생성한 데이터에서 조건을 또 거는 것
     
     ```sql
     SELECT 
     	Country,
     	COUNT(CustomerID) // Country가 같은 애들의 개수
     FROM Customers
     GROUP BY Country
HAVING COUNT(CustomerID) >= 10
     ```
     

##### JOIN

- 얘랑 엮어서

![image](https://user-images.githubusercontent.com/41130448/105037846-f2857880-5aa1-11eb-917a-46250bad6fda.png)

- 각 테이블을 키로 엮은 것

- **INNER JOIN**

  Customers와 Orders의 교집합을 가져오는데, 그 교집합의 기준은 ***CustomerID***

  물건을 산 회원의 데이터만 가져오기

  ```sql
  SELECT 열이름 FROM 테이블명A INNER JOIN 테이블명B ON B.Key = A.key;
  ```

  

<img src="https://user-images.githubusercontent.com/41130448/105037929-0cbf5680-5aa2-11eb-8482-483b6890a657.png" alt="image" style="zoom: 33%;" />

​       테이블 A와 테이블 B 중 key 값이 같은 정보를 모두 합쳐라

- ```sql
  SELECT C.CustomerID, O.OrderID
  FROM Customers AS C
  INNER JOIN Orders AS O
  ON O.CustomerID = C.CustomerID
  ```

- **LEFT JOIN**

  Customers는 냅두고 Orders는 가져와라

  전체 회원의 구매 내역을 가져와라

  ```
  SELECT 열이름 FROM 테이블명A LEFT JOIN 테이블명B ON B.Key = A.key;
  ```

  

  <img src="https://user-images.githubusercontent.com/41130448/105038839-2ca34a00-5aa3-11eb-98e5-17a1d7850f36.png" alt="image" style="zoom:33%;" />

  ```sql
  SELECT C.CustomerID, O.OrderID
  FROM Customers AS C
  LEFT JOIN Orders AS O
  ON O.CustomerID = C.CustomerID
  ```

- 그 외

<img src="https://user-images.githubusercontent.com/41130448/105039376-d5ea4000-5aa3-11eb-911d-a3900b0e7b7e.png" alt="image"  />



