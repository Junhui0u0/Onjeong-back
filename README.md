<br />
<div align="center">
  <a href="https://www.onjeong-app.com/">
    <img src="https://onjeong-prod.s3.ap-northeast-2.amazonaws.com/profile/24c4b2d3-be1c-4bd8-8d95-79e7a66ce23eonjeong%20logo.png?s=200&v=4" alt="Logo" width="80" height="80">
  </a>
  <h3 align="center">Onjeong-Backend</h3>

  <p align="center">
    온정의 backend 부분 github입니다.
    <br />
    <a href="https://github.com/On-jeong"><strong>1. Explore the Organization</strong></a><br>
    <a href="https://github.com/On-jeong/Onjeong-back"><strong>2. Explore Back-end Repository</strong></a><br>
    <a href="https://www.onjeong-app.com/swagger-ui.html/"><strong>3. Explore API Documents</strong></a><br>    
    <br />
    <br />
    <!-- <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a> -->
    <!-- · -->
  </p>
  

## 박준희 (Backend)
저는 `family`, `user`, `profile`, `anniversary`, `board` 도메인 개발과 `AWS EC2 관리`, `DNS 등록`, `로드밸런싱`, `CI/CD`를 담당했습니다. <br><br><br><br>

## 프로젝트 소개
**온정**은 가족들과 같이 꽃을 키워나가며 소통하는 **가족커뮤니케이션 앱**입니다. <br/>

- 저희 서비스를 통해 가족 구성원들이 서로 소통하면서 아래와 같은 `여러 활동들`을 경험할 수 있습니다.
   - 가족 프로필 생성
   - 기념일 작성
   - 오늘의 기록 작성
   - 이주의 문답
   - 편지 보내기
- 이 다양한 활동을 통해 가족구성원들이 함께 꽃을 피워냄으로써 **가족애**를 키우는 것이 저희 서비스 목표입니다. <br><br><br><br>


## AWS 클라우드 구조
<div style="text-align: center;">
<img src="https://onjeong-prod.s3.ap-northeast-2.amazonaws.com/git/%EC%98%A8%EC%A0%95+aws+%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C+%EA%B5%AC%EC%A1%B0_2.png" width="800" height="350" />
</div>  
<br><br><br>


##  인프라 구조
- 운영서버 CI/CD
<img src="https://onjeong-prod.s3.ap-northeast-2.amazonaws.com/git/%EC%98%A8%EC%A0%95+%EC%9A%B4%EC%98%81+CI%2CCD+1.png"/>
<br><br>

- 개발서버 CI/CD
<img src="https://onjeong-prod.s3.ap-northeast-2.amazonaws.com/git/%EC%98%A8%EC%A0%95+%EA%B0%9C%EB%B0%9C+CI%2CCD+1.png"/>
<br><br><br>


## 기술 스택
-   Java 11
-   Spring Boot 2.4.2
-   Spring Data JPA
-   Spring Security, JWT, Firebase
-   MySQL, H2, Spring Data Redis
-   Spring Batch
-   JUnit5, Mockito
-   Nginx
-   Docker, Jenkins, Github Action
-   Swagger
-   Amazon Web Services (EC2, RDS, S3, Route 53, VPC, Internet gateway, NAT gateway, NLB, Certificate Manager)
<br><br><br><br><br>

##  패키지 구조
- api와 batch 작업은 서로 독립적으로 이뤄지기때문에 모듈별로 분리하여 재사용, 유지보수, 에러파악 측면에서의 이점을 얻고자 `멀티모듈(Multi Module)`을 도입했습니다.
- 저희는 **api 모듈**, **batch 모듈**, api와 batch가 공통으로 사용하고 있는 **common 모듈**로 3개의 모듈로 분리했습니다.

<details>
<summary><b>구조</b></summary>

```txt
└── module-api
    ├── src
    │   ├── main
    │   │   └── java.com.example.onjeong
    │   │       ├── anniversary
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── board
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── coin
    │   │       │   └── service
    │   │       ├── home
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── infra
    │   │       │   └── controller
    │   │       ├── mail
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── notification
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── profile
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── question
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       ├── user
    │   │       │   ├── controller
    │   │       │   └── service
    │   │       └── OnjeongApplication.java
    │   │     
    |   |    └── resources
    |   |           ├── application.properties
    |   |           ├── application-dev.properties
    |   |           ├── application-prod.properties
    |   |           ├── server-application.yml
    |   |           └── firebase
    |   |           |      └── firebase_service_key.json
    |   |           └── static
    |   |                  └── index.html
    │   └── test.java.com.example.onjeong
    |
└── module-batch
    ├── src.main.java.com.example.onjeong
    │   ├── config
    │   │     └── JobConfig.java
    |   ├── scheduler
    │   │     └── JobScheduler.java
    │   └── BatchApplication.java
    |
└── module-common
    ├── src.main.java.com.example.onjeong
    │   ├── Config
    │   │      └── ....
    │   ├── S3
    │   │      └── ....
    │   ├── anniversary
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── board
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── coin
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── service
    │   ├── family
    │   │      ├── domain
    │   │      ├── repository
    │   │      └── exception
    │   ├── home
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── mail
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── notification
    │   │      ├── domain
    │   │      ├── dto
    │   │      └── repository
    │   ├── profile
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── question
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      └── exception
    │   ├── user
    │   │      ├── Auth
    │   │      ├── redis
    │   │      ├── domain
    │   │      ├── dto
    │   │      ├── repository
    │   │      ├── service
    │   │      └── exception
    │   ├── error
    │   │      ├── ErrorCode.java
    │   │      ├── ErrorController.java
    │   │      ├── ErrorResponse.java
    │   │      └── GlobalExceptionHandler.java
    │   ├── result
    │   │      ├── ResultCode.java
    │   │      └── ResultResponse.java
    │   └── util
               ├── AuthUtil.java
               └── ProfileUtil.java
```
</details>
<br><br><br><br>

## Response Convention
1. 통일된 Error Response 객체
    - Error Response JSON
      ```json
        {
            "status": 500,
            "message": "DATETYPE CONVERSE FAIL",
            "code": "DATETYPE-ERR-500"
        }
        ```
      - status : http status code를 작성합니다.
      - message : 에러에 대한 내용을 작성합니다.
      - code : 에러에 할당되는 유니크한 코드 값입니다.

    - Error Response 객체
        ```java
        @Getter
        @Setter
        public class ErrorResponse {
            private int status;
            private String message;
            private String code;

            public ErrorResponse(ErrorCode errorCode){
                this.status = errorCode.getStatus();
                this.message = errorCode.getMessage();
                this.code = errorCode.getErrorCode();
            }
        }
        ```
    - Error Code 정의
        ```java
        @Getter
        @AllArgsConstructor
        public enum ErrorCode {
            // Common
            NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
            INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
            ....
    
            // User
            USER_NOTEXIST(500,"U001","LOGIN USER NOT EXIST"),
            USER_UNAUTHORIZED(401,"U002","USER UNAUTHORIZED"),
            ....
            ;
            
            final private int status;
            final private String errorCode;
            final private String message;
        }
        ```
    - @RestControllerAdvice로 모든 예외를 핸들링
        ```java
        @Slf4j
        @RestControllerAdvice
        public class GlobalExceptionHandler {
          @ExceptionHandler(Exception.class)
          public ResponseEntity<ErrorResponse> handleException(Exception ex){
              log.error("handleException",ex);
              final ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
              return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
          }
          
          @ExceptionHandler(UserNotExistException.class)
          public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException ex){
              log.error("handleUserNotExistException",ex);
              final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
              return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
          }
          ....
        }
        ```
        
2. 통일된 Result Response 객체
    - Result Response JSON
      ```json
        {
          "status": 200,
          "code": "U001",
          "message": "회원가입에 성공했습니다.",
          "data": ""
        }
        ```
      - status : http status code를 작성합니다.
      - code : 결과에 할당되는 유니크한 코드 값입니다.
      - message : 결과에 대한 내용을 작성합니다.
      - data : 결과 객체를 JSON 형태로 반환합니다.

    - Result Response 객체
      ```java
      @Getter
      public class ResultResponse {
          private final int status;
          private final String code;
          private final String message;
          private final Object data;

          public ResultResponse(ResultCode resultCode, Object data) {
              this.status = resultCode.getStatus();
              this.code = resultCode.getCode();
              this.message = resultCode.getMessage();
              this.data = data;
          }

          public static ResultResponse of(ResultCode resultCode, Object data) {
              return new ResultResponse(resultCode, data);
          }

          public static ResultResponse of(ResultCode resultCode) {
              return new ResultResponse(resultCode, "");
          }
      }
      ```
    - @RestController에서 통일된 응답 사용
    ```java
    @RestController
    @RequiredArgsConstructor
    public class UserController {
        private final UserService userService;
        
        @ApiOperation(value="가족회원이 없는 회원 가입")
        @PostMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResultResponse> signUp(@RequestBody UserJoinDto userJoinDto){
            ...
            
            return ResponseEntity.ok(ResultResponse.of(ResultCode.REGISTER_SUCCESS));
        }
        ...
    }
    ```
<br><br><br>


## Commit Convention
```txt
Type: Subject
ex) Feat: 로그인 로직 추가
```
- <b>Type</b>
  - feat: 새로운 기능 추가
  - fix: 버그 수정
  - docs : 문서 수정
  - style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
  - refactor : 코드 리펙토링
  - test : 테스트 코드, 리펙토링 테스트 코드 추가
  - chore : 빌드 업무 수정, 패키지 매니저 수정
<br><br><br><br><br>


## PR Convention
```txt
## 개요
## 작업사항
## 변경로직
### 변경전
### 변경후
## 사용방법
## 기타
```
<br><br><br>

## ERD
<p align="center"><a href="https://www.erdcloud.com/d/9JxX7unXDjeZN5XHC"><img src="https://img.shields.io/badge/ERD Cloud-946CEE?style=for-the-badge"/></a></p>
<p align="right">(<a href="#top">back to top</a>)</p>
