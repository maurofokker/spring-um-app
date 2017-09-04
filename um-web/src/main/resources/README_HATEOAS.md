### Documentation

[https://en.wikipedia.org/wiki/HATEOAS]

[https://spring.io/understanding/HATEOAS]

[http://projects.spring.io/spring-hateoas/]

[https://spring.io/guides/gs/rest-hateoas/]

[http://docs.spring.io/spring-hateoas/docs/current/reference/html/]

[https://en.wikipedia.org/wiki/Link_relation]

[http://www.iana.org/assignments/link-relations/link-relations.xhtml]

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
```java
public class RoleResource extends ResourceSupport {

    private final Role role;

    public RoleResource(final Role role) {
        this.role = role;

        this.add(linkTo(RoleHateoasController.class).withRel("roles"));
    }

    //
    public Role getRole() {
        return role;
    }

}
```

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
##### more complete resource with spring hateoas impl
* add another Link To RoleResource wrap Class
```java
public class RoleResource extends ResourceSupport {

    private final Role role;

    public RoleResource(final Role role) {
        this.role = role;

        this.add(linkTo(RoleHateoasController.class).withRel("roles"));
        // another type of link to see how powerful it is
        // - not linking to the entire controller but to a specific method of the controller
        //   pointing to the operation that is exposed via that method, in this case findOne(:id)
        //   accepting the id argument
        // - is specifying the REL Type (with withSelfRel() method), instead of using a custom REL Type,
        //   are using the self REL Type because the operation findOne() does map to the self REL Type
        //   seeing how it points to the role
        this.add(linkTo(methodOn(RoleHateoasController.class, role).findOne(role.getId())).withSelfRel());
    }

    //
    public Role getRole() {
        return role;
    }

}
```

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
        "description": "dlytrI",
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
        },
        "self": {
            "href": "http://localhost:8086/um-webhateoas/roles/8"
        }
    }
}
```
* add a self rel to the resource 
