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
http://localhost:8086/um-web/oauth/token?grant_type=password&client_id=live-test&username=admin@fake.com&password=adminpass
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
``` 
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMzAxNDAsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJlYWUzYTczYy03Y2NlLTRkNDEtYWMzNi1jZjZjZDZmMDg5ODkiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiLCJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.E0nvIfzVolYYHTr3ZLKghmA43q7wUQxoCNW1fEuIkes",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6ImVhZTNhNzNjLTdjY2UtNGQ0MS1hYzM2LWNmNmNkNmYwODk4OSIsImV4cCI6MTUwNDIxMjk0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJjZDJmNjk2NS0zZGEyLTQ1NjEtYTNhYy0yMjU4ZmE5Y2I2ODAiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.il0XUjG0m3tObvtsXS8DpdPQv9dDseII078EWy8Gwb4",
    "expires_in": 3599,
    "scope": "um-web read write trust",
    "jti": "eae3a73c-7cce-4d41-ac36-cf6cd6f08989"
}
```

### use of refresh token
```
http://localhost:8086/um-web/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6ImVhZTNhNzNjLTdjY2UtNGQ0MS1hYzM2LWNmNmNkNmYwODk4OSIsImV4cCI6MTUwNDIxMjk0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJjZDJmNjk2NS0zZGEyLTQ1NjEtYTNhYy0yMjU4ZmE5Y2I2ODAiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.il0XUjG0m3tObvtsXS8DpdPQv9dDseII078EWy8Gwb4
Basic bGl2ZS10ZXN0OkgwbDRNdU5kMA==
(live-test:H0l4MuNd0)
```
- we got a new access token providing the refresh token (see expires_in) and not the credentials
``` 
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQxMzA0MTgsInVzZXJfbmFtZSI6ImFkbWluQGZha2UuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJkZWI3YTg0Ni0wODc4LTQ0NjktOWVhNS1kNWE2ZDllZjdkNjgiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QiLCJzY29wZSI6WyJ1bS13ZWIiLCJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdfQ.3nNQwqRweQkx9hQEPIZGuC_PIYJyKyxkzQID9tOos9o",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBmYWtlLmNvbSIsInNjb3BlIjpbInVtLXdlYiIsInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6ImRlYjdhODQ2LTA4NzgtNDQ2OS05ZWE1LWQ1YTZkOWVmN2Q2OCIsImV4cCI6MTUwNDIxMjk0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9ST0xFX1dSSVRFIiwiUk9MRV9VU0VSX1dSSVRFIiwiUk9MRV9ST0xFX1JFQUQiLCJST0xFX1VTRVJfUkVBRCIsIlJPTEVfUFJJVklMRUdFX1JFQUQiLCJST0xFX1BSSVZJTEVHRV9XUklURSJdLCJqdGkiOiJjZDJmNjk2NS0zZGEyLTQ1NjEtYTNhYy0yMjU4ZmE5Y2I2ODAiLCJjbGllbnRfaWQiOiJsaXZlLXRlc3QifQ.q0TpepiUj7cjguBpcAgmSTyGT07cf5kzdzwXpkiEtXQ",
    "expires_in": 3599,
    "scope": "um-web read write trust",
    "jti": "deb7a846-0878-4469-9ea5-d5a6d9ef7d68"
}
```
