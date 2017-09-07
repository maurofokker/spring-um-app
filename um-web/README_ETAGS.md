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
 ...
 ETag: "f88dd058fe004909615a64f01be66a7"
 ...
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
    If-None-Match: "f88dd058fe004909615a64f01be66a7"
```
    * no response body
```
HTTP/1.1 304 Not Modified
ETag: "f88dd058fe004909615a64f01be66a7"
```