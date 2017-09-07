### Types of Etags implementations
* Deep
    * the request uses Etag during processing
    * based on the Etag determines if the resource has changed
    * if the resource hasn't changed it will skip retrieving it
* Shallow
    * request is processed as normal
    * the response needs to be intercepted before to go out
    * the hash needs to be calculated and make the decision at the end (if resource has changed)

### Conditional Requests
* An _If-*_ header turns a standard GET into a conditional GET
* Headers used with ETags:
    * _If-None-Match_
    * _If-Match_
* If representation of resource hasnt change then a _304 Not Modified_ Status Code is returned with empty body

### Example

#### Get etag
```
GET http://localhost:8086/um-web/api/roles/8
Headers:
    Accept: application/json
    Authorization: Bearer <token>
```
* Response header
```
Access-Control-Allow-Headers →X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version
Access-Control-Allow-Methods →POST, PUT, GET, OPTIONS, DELETE
Access-Control-Allow-Origin →*
Access-Control-Expose-Headers →X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type
Access-Control-Max-Age →3600
Content-Length →340
Content-Type →application/json;charset=UTF-8
Date →Thu, 07 Sep 2017 20:55:21 GMT
ETag →"07eeafa35ada55f453e03e2b190c4e089"
Link →<http://localhost:8086/um-web/roles>; rel="collection"
Server →Apache-Coyote/1.1
X-Application-Context →application:8086
X-Content-Type-Options →nosniff
X-Frame-Options →DENY
X-XSS-Protection →1; mode=block
```
* Response body
```
{
    "id": 8,
    "name": "ROLE_USER",
    "description": "VyxBNv",
    "privileges": [
        {
            "id": 5,
            "name": "ROLE_USER_READ",
            "description": null
        },
        {
            "id": 3,
            "name": "ROLE_ROLE_READ",
            "description": null
        },
        {
            "id": 1,
            "name": "ROLE_PRIVILEGE_READ",
            "description": null
        }
    ]
} 
```
* Conditional Request
``` 
GET http://localhost:8086/um-web/api/roles/8
Headers:
    Accept: application/json
    Authorization: Bearer <token>
    If-None-Match: "07eeafa35ada55f453e03e2b190c4e089"
```
    * no response body
```
HTTP/1.1 304 Not Modified
ETag: "07eeafa35ada55f453e03e2b190c4e089"
```

* Documentation

[http://www.baeldung.com/2013/01/11/etags-for-rest-with-spring/]

[http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/filter/ShallowEtagHeaderFilter.html]

    * problem with etag config and security
[https://stackoverflow.com/questions/26742207/add-shallowetagheaderfilter-in-spring-boot-mvc]
[https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/headers.html]