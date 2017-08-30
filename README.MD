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
