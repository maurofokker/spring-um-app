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
