# Spring user management with spring boot

### Info

- parent project and modules created with intellij
- parent is an empty spring-boot project 
    - added some normal maven projects
    - added spring boot web module
- Java 8
- Maven 3
- Spring Boot 1.5.4



### JWT integration

#### jwt token request
```
http://localhost:8086/um-web/oauth/token?grant_type=password&client_id=live-test&username=admin@fake.com&password=adminpass
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
#### jwt token response

``` 
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMjYzNDUsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMTU3YjA4My1kMmU4LTQxOWItYjkxNS1kNDJmZDc5MzVmNzYiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiXX0.38cYcVx-Sln9pnURV0DZkdKQccFSbjPmPs-ych782S0",
    "token_type": "bearer",
    "expires_in": 3599,
    "scope": "um-web",
    "jti": "0157b083-d2e8-419b-b915-d42fd7935f76"
}
```

- This could be tested with `RoleSimpleLiveTest` with server running
#### jwt token 

`"access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMjYzNDUsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMTU3YjA4My1kMmU4LTQxOWItYjkxNS1kNDJmZDc5MzVmNzYiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiXX0.38cYcVx-Sln9pnURV0DZkdKQccFSbjPmPs-ych782S0",`

| JWT Part | Token | 
|---|---| 
| header | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 | 
| payload | eyJleHAiOjE1MDQxMjYzNDUsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMTU3YjA4My1kMmU4LTQxOWItYjkxNS1kNDJmZDc5MzVmNzYiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiXX0 |
| signature | 38cYcVx-Sln9pnURV0DZkdKQccFSbjPmPs-ych782S0 |

### refresh token (after refresh token conf in Authorizarion Server)
```
GET
http://localhost:8086/um-web/oauth/token?grant_type=password&client_id=live-test&username=admin@fake.com&password=adminpass
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
``` 
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMzAxNDAsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJlYWUzYTczYy03Y2NlLTRkNDEtYWMzNi1jZjZjZDZmMDg5ODkiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiLCJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.E0nvIfzVolYYHTr3ZLKghmA43q7wUQxoCNW1fEuIkes",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjZjNGQwZDE1LTUzNDUtNGVhNi05YWZhLTZkNGRhMjc3NjVjNSIsImV4cCI6MTUwNDI4Nzc1MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMzM2NzJkNy0zMzAwLTQ1OWQtYTkwMi0xMWUxNzBmNzZjMjQiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.uYu-yqHBX7cYcJ-Sm0H2ZvBwKsvgZR0YlObExbm7O78",
    "expires_in": 3599,
    "scope": "um-web read write trust",
    "jti": "eae3a73c-7cce-4d41-ac36-cf6cd6f08989"
}
```

### use of refresh token
```
POST http://localhost:8082/um-webapp/oauth/token?grant_type=refresh_token&refresh_token={refresh_token}
http://localhost:8086/um-web/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjZjNGQwZDE1LTUzNDUtNGVhNi05YWZhLTZkNGRhMjc3NjVjNSIsImV4cCI6MTUwNDI4Nzc1MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMzM2NzJkNy0zMzAwLTQ1OWQtYTkwMi0xMWUxNzBmNzZjMjQiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.uYu-yqHBX7cYcJ-Sm0H2ZvBwKsvgZR0YlObExbm7O78
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
- we got a new access token providing the refresh token (see expires_in) and not the credentials
``` 
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMzA0MTgsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJkZWI3YTg0Ni0wODc4LTQ0NjktOWVhNS1kNWE2ZDllZjdkNjgiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiLCJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.3nNQwqRweQkx9hQEPIZGuC_PIYJyKyxkzQID9tOos9o",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6ImIwOWRhYTI5LTM3YTUtNDE3MS05ZjMzLWQzMGRkNDJkYWQ3NCIsImV4cCI6MTUwNDI4Nzc1MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiIwMzM2NzJkNy0zMzAwLTQ1OWQtYTkwMi0xMWUxNzBmNzZjMjQiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.xHPV98TsFVZ8jLlXRtQy5yXTNT3rlCyOqhii1MX_iSA",
    "expires_in": 3599,
    "scope": "um-web read write trust",
    "jti": "deb7a846-0878-4469-9ea5-d5a6d9ef7d68"
}
```

### check token endpoint
```
GET http://localhost:8086/um-web/oauth/check_token?token={access_token}
http://localhost:8086/um-web/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQyMDUwMTAsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJiMDlkYWEyOS0zN2E1LTQxNzEtOWYzMy1kMzBkZDQyZGFkNzQiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiLCJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.T46Ebs5hGAEMi3lEKe3VbhWzltgv5UdZYmdLYQlXjS8
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
- we got a new access token providing the refresh token (see expires_in) and not the credentials
``` 
{
    "exp": 1504205010,
    "user_name": "admin@fake.com",
    "authorities": [
        "ROLE_ROLE_WRITE",
        "ROLE_USER_WRITE",
        "ROLE_ROLE_READ",
        "ROLE_USER_READ",
        "ROLE_PRIVILEGE_READ",
        "ROLE_PRIVILEGE_WRITE"
    ],
    "jti": "b09daa29-37a5-4171-9f33-d30dd42dad74",
    "client_id": "live-test",
    "scope": [
        "um-web",
        "read",
        "write",
        "trust"
    ]
}
```
