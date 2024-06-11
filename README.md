### 'Login API Flow '


### A)  프로젝트 설정
1. 프로젝트 설정
    - Spring Boot
    - Gradle
    - Java 17
2. 데이터베이스 설정
    - H2
    - Redis
    - JPA
    - QueryDSL
3. 라이브러리
    - spring security
    - JWT

----

### B) 시퀀스 다이어그램
![sd](https://github.com/antigravity-official/backend-product-amount/assets/73773527/69236cd9-7179-4903-95fe-e141ce6f8e60)



----

### C) API 사용 방법


**1. 로그인 API**

■ Request
> POST localhost:8080/account/signin
```
{
    "userId" : "testId",
    "password" : "1234"
}
```

■ Response
```
{
    "data": {
        "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJRVIiXSwiVVNFUklEX0tFWSI6MSwiaWF0IjoxNzE2NzkzNzc0LCJleHAiOjE3MzQsImp0aSI6IjBiNjZhYWY4L0NTM1ZDIzN2FjMSJ9.ZCF4_TJN5KDrb5_A4GjAdWsJr6qqvVj4WpmfzybD-yv0iOEGjRlkqpBMURiTjOVN9gvzb63j2GnTCrnkoqLK1_S5vRd-Cvjzeq_DhNLAWxZafGReHKwO1ITX8qs_vITCCh4L9F0mTL7y0TSfTaoXkJfmWOFqmWr9QK0iuDcVvbdAQMr_-wFXcK0SoHLUCtlhlLPKTKJyR7fw",
        "accessTokenExpiredAt": "2024-08-25 16:09:34",
        "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJVU0VSSUMTY3OTM3NzUsImV4cCI6MTcyNDU2OTc3NSwianRpIjoiNjUzGNlLWEzOWMtMThmZGU5ZWJkZGJlIn0.rdngFEQR_SIspSjV2x-UFxtBDsB7Tt6jhEwUA2iCkRSY5l2SoiK3_lfBJj9doJ9AZpSdvdWvVxaNFACrB1tJrv56UGdVa05bVEX4_97LRwySPxryMPlCRL2gcs6W-0siPB9ySb73SJ0kJX0Fd879o0YmnR2TibsdWbh0Y2bdCsIo2owyI34NlE97WITL7n2SC6nMvJLpjufLQbpzaIMCf5E7frBWw",
        "refreshTokenExpiredAt": "2024-08-25 16:09:35"
    },
    "status": 0,
    "message": "success"
}
```

----
**2. 내 정보 조회 **

■ Request
> GET localhost:8080/user

■ Response
```
{
    "data": {
        "id": 1,
        "userId": "testId",
        "name": "홍길동",
        "createdAt": "2024-05-26T00:00:00"
    },
    "status": 0,
    "message": "success"
}
```

### D) 정책
1. JWT 토큰 기반 인증
2. 로그인 성공 시 토큰을 발급받는다.
3. 발급받은 Access Token 으로 사용자 정보 가져오기를 요청할 수 있다.
----
### F) Response Error
- 2XXX Client error
- 4XXX Auth error
- 9XXX Server Error
- 6XXX Service Error


|  Http <br/>Status  |   Code    | label                | 에러 메세지            | 설명                  |
|:------------------:|:---------:|----------------------|-------------------|---------------------|
|        200         |   6001    | MEMBER_INVALID_ACCOUNT      | 아이디 또는 비밀번호를 확인해 주세요.      | 아이디 또는 비빌번호가 불일치 시 발생한다. |
|        200         |   6002    | MEMBER_STATUS_NOT_ACTIVE    |사용할 수 없는 계정입니다.  | 탈퇴회원의 로그인 요청시 발생한다. |

