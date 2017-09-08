### Throttling - RateLimit
* a single user can only access for fair use
* to make sure that abusive clients are stopped or slowed from accessing system
* this implementation is made with
    * AOP to controll methods
    * Guava Rate Limiter to control access
* @RateLimit(1) ensure that no client can send more than 1 req per second to APi
* In production environment the limit shold not be too restrictive

### Documentation

[http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html]

[https://google.github.io/guava/releases/20.0/api/docs/index.html?com/google/common/util/concurrent/RateLimiter.html]