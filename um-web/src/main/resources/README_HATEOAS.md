### Documentation

[https://en.wikipedia.org/wiki/HATEOAS]

[https://spring.io/understanding/HATEOAS]

### Link Header 

#### Request

``` 
GET http://localhost:8086/um-web/api/privileges/1
Header:
Bearer <JWT_Token>
```

#### HATEOAS Response in header

``` 
Date →Mon, 04 Sep 2017 15:39:19 GMT
Expires →0
// important link rel type
Link →<http://localhost:8086/um-web/privileges>; rel="collection"

Pragma →no-cache
Server →Apache-Coyote/1.1
```

### Spring HATEOAS

* Less intrusive than Spring Data Rest
* Can be added with it dependency
```xml
<dependency>
    <groupId>org.springframework.hateoas</groupId>
    <artifactId>spring-hateoas</artifactId>
</dependency>
```

#### Simple ResourceSupport

* add the resource as object
* add links references (if they exists) at the end 

#### compare of two response resources
##### resource with no spring hateoas impl
``` 
GET http://localhost:8086/um-web/api/roles/8
Header:
Bearer <JWT_Token>
```

```json
{
    "id": 8,
    "name": "ROLE_USER",
    "description": "HOaztt",
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
##### resource with spring hateoas impl
``` 
GET http://localhost:8086/um-web/hateoas/roles/8
Header:
Bearer <JWT_Token>
```

```json
{
    "role": {
        "id": 8,
        "name": "ROLE_USER",
        "description": "nqOjHB",
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
    },
    "_links": {
        "roles": {
            "href": "http://localhost:8086/um-web/hateoas/roles"
        }
    }
}
```
