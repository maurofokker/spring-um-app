### Swagger2 Api Doc

#### Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.2.2</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.2.2</version>
    </dependency>
</dependencies>
```

#### Configuration
```java
@Configuration
@ComponentScan({ "com.maurofokker.um.web", "com.maurofokker.common.web" })
@EnableWebMvc
@EnableSwagger2
public class UmWebConfig extends WebMvcConfigurerAdapter {
    /*
        entry point to control swagger2 config using builder pattern
         */
        @Bean
        public Docket mainConfig() {
            // @formatter:off
            return new Docket(DocumentationType.SWAGGER_2)
                    .select().apis(RequestHandlerSelectors.any()) // pick up request handlers that define spring api any() is bc API exists in this app
                    .paths(PathSelectors.any()) // same as above to cover paths of API
                    .build()
                    .pathMapping("/api")        // base path of API in servlet
                    .directModelSubstitute(LocalDate.class, String.class) // global substitutions of models, local dates replaced by strings
                    .genericModelSubstitutes(ResponseEntity.class) // not document response entity but body of RE, to get the actual data type that gets wrapped in the response entity by spring
                    // the idea is a documentation that be spring agnostic
                    ;
            // @formatter:on
        }
    
        /*
        where swagger ui is
         */
        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
}
```

#### API Data (with jwt)
##### Request
``` 
GET http://localhost:8086/um-web/v2/api-docs
Header:
    Authorization: Bearer <token>
```
##### Response
```json
{
    "swagger": "2.0",
    "info": {
        "description": "Api Documentation",
        "version": "1.0",
        "title": "Api Documentation",
        "termsOfService": "urn:tos",
        "contact": {
            "name": "Contact Email"
        },
        "license": {
            "name": "Apache 2.0",
            "url": "http://www.apache.org/licenses/LICENSE-2.0"
        }
    },
    "host": "localhost:8086",
    "basePath": "/um-web",
    "tags": [
        {
            "name": "token-key-endpoint",
            "description": "Token Key Endpoint"
        },
        {
            "name": "authorization-endpoint",
            "description": "Authorization Endpoint"
        },
        {
            "name": "authentication-controller",
            "description": "Authentication Controller"
        },
        {
            "name": "user-controller",
            "description": "User Controller"
        },
        {
            "name": "check-token-endpoint",
            "description": "Check Token Endpoint"
        },
        {
            "name": "whitelabel-error-endpoint",
            "description": "Whitelabel Error Endpoint"
        },
        {
            "name": "privilege-controller",
            "description": "Privilege Controller"
        },
        {
            "name": "role-controller",
            "description": "Role Controller"
        },
        {
            "name": "token-endpoint",
            "description": "Token Endpoint"
        },
        {
            "name": "redirect-controller",
            "description": "Redirect Controller"
        },
        {
            "name": "whitelabel-approval-endpoint",
            "description": "Whitelabel Approval Endpoint"
        }
    ],
    "paths": {
        "/api/api/authentication": {
            "get": {
                "tags": [
                    "authentication-controller"
                ],
                "summary": "createAuthentication",
                "operationId": "createAuthenticationUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/User"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/privileges": {
            "get": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "findAllPaginatedAndSorted",
                "operationId": "findAllPaginatedAndSortedUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "sortOrder",
                        "in": "query",
                        "description": "sortOrder",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "page",
                        "in": "query",
                        "description": "page",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "size",
                        "in": "query",
                        "description": "size",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "sortBy",
                        "in": "query",
                        "description": "sortBy",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "array",
                            "items": {
                                "$ref": "#/definitions/Privilege"
                            }
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "post": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "create",
                "operationId": "createUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/Privilege"
                        }
                    }
                ],
                "responses": {
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/privileges/count": {
            "get": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "count",
                "operationId": "countUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/privileges/{id}": {
            "get": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "findOne",
                "operationId": "findOneUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/Privilege"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "update",
                "operationId": "updateUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    },
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/Privilege"
                        }
                    }
                ],
                "responses": {
                    "200": {},
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "privilege-controller"
                ],
                "summary": "delete",
                "operationId": "deleteUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/api/roles": {
            "get": {
                "tags": [
                    "role-controller"
                ],
                "summary": "findAllPaginatedAndSorted",
                "operationId": "findAllPaginatedAndSortedUsingGET_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "sortOrder",
                        "in": "query",
                        "description": "sortOrder",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "page",
                        "in": "query",
                        "description": "page",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "size",
                        "in": "query",
                        "description": "size",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "sortBy",
                        "in": "query",
                        "description": "sortBy",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "array",
                            "items": {
                                "$ref": "#/definitions/Role"
                            }
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "post": {
                "tags": [
                    "role-controller"
                ],
                "summary": "create",
                "operationId": "createUsingPOST_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/Role"
                        }
                    }
                ],
                "responses": {
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/roles/count": {
            "get": {
                "tags": [
                    "role-controller"
                ],
                "summary": "count",
                "operationId": "countUsingGET_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/roles/{id}": {
            "get": {
                "tags": [
                    "role-controller"
                ],
                "summary": "findOne",
                "operationId": "findOneUsingGET_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/Role"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "role-controller"
                ],
                "summary": "update",
                "operationId": "updateUsingPUT_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    },
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/Role"
                        }
                    }
                ],
                "responses": {
                    "200": {},
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "role-controller"
                ],
                "summary": "delete",
                "operationId": "deleteUsingDELETE_1",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/api/users": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "summary": "findAllPaginated",
                "operationId": "findAllPaginatedUsingGET_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "page",
                        "in": "query",
                        "description": "page",
                        "required": true,
                        "type": "string"
                    },
                    {
                        "name": "size",
                        "in": "query",
                        "description": "size",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "array",
                            "items": {
                                "$ref": "#/definitions/User"
                            }
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "post": {
                "tags": [
                    "user-controller"
                ],
                "summary": "create",
                "operationId": "createUsingPOST_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/User"
                        }
                    }
                ],
                "responses": {
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/users/count": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "summary": "count",
                "operationId": "countUsingGET_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "integer",
                            "format": "int64"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/users/current": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "summary": "current",
                "operationId": "currentUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/User"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/api/users/user": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "head": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingHEAD",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "post": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "options": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingOPTIONS",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "patch": {
                "tags": [
                    "user-controller"
                ],
                "summary": "user",
                "operationId": "userUsingPATCH",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "user",
                        "description": "user",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/UmUser"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/api/users/{id}": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "summary": "findOne",
                "operationId": "findOneUsingGET_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/User"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "user-controller"
                ],
                "summary": "update",
                "operationId": "updateUsingPUT_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    },
                    {
                        "in": "body",
                        "name": "resource",
                        "description": "resource",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/User"
                        }
                    }
                ],
                "responses": {
                    "200": {},
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "user-controller"
                ],
                "summary": "delete",
                "operationId": "deleteUsingDELETE_2",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "description": "id",
                        "required": true,
                        "type": "integer",
                        "format": "int64"
                    }
                ],
                "responses": {
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/oauth/authorize": {
            "get": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "head": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingHEAD",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "post": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "options": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingOPTIONS",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "patch": {
                "tags": [
                    "authorization-endpoint"
                ],
                "summary": "authorize",
                "operationId": "authorizeUsingPATCH",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    },
                    {
                        "in": "body",
                        "name": "sessionStatus",
                        "description": "sessionStatus",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/SessionStatus"
                        }
                    },
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/oauth/check_token": {
            "get": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "head": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingHEAD",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "post": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "options": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingOPTIONS",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "patch": {
                "tags": [
                    "check-token-endpoint"
                ],
                "summary": "checkToken",
                "operationId": "checkTokenUsingPATCH",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "name": "token",
                        "in": "query",
                        "description": "value",
                        "required": true,
                        "type": "string"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/oauth/confirm_access": {
            "get": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "head": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingHEAD",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "post": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "options": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingOPTIONS",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "patch": {
                "tags": [
                    "whitelabel-approval-endpoint"
                ],
                "summary": "getAccessConfirmation",
                "operationId": "getAccessConfirmationUsingPATCH",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "model",
                        "description": "model",
                        "required": false,
                        "schema": {
                            "type": "object"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/oauth/error": {
            "get": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "head": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingHEAD",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "post": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "put": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingPUT",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "delete": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingDELETE",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "options": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingOPTIONS",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            },
            "patch": {
                "tags": [
                    "whitelabel-error-endpoint"
                ],
                "summary": "handleError",
                "operationId": "handleErrorUsingPATCH",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/ModelAndView"
                        }
                    },
                    "204": {
                        "description": "No Content"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    }
                }
            }
        },
        "/api/oauth/token": {
            "get": {
                "tags": [
                    "token-endpoint"
                ],
                "summary": "getAccessToken",
                "operationId": "getAccessTokenUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/OAuth2AccessToken"
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            },
            "post": {
                "tags": [
                    "token-endpoint"
                ],
                "summary": "postAccessToken",
                "operationId": "postAccessTokenUsingPOST",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    },
                    {
                        "name": "parameters",
                        "in": "query",
                        "description": "parameters",
                        "required": true,
                        "type": "ref"
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/OAuth2AccessToken"
                        }
                    },
                    "201": {
                        "description": "Created"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/oauth/token_key": {
            "get": {
                "tags": [
                    "token-key-endpoint"
                ],
                "summary": "getKey",
                "operationId": "getKeyUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "parameters": [
                    {
                        "in": "body",
                        "name": "principal",
                        "description": "principal",
                        "required": false,
                        "schema": {
                            "$ref": "#/definitions/Principal"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "object",
                            "additionalProperties": {
                                "type": "string"
                            }
                        }
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/privilege": {
            "get": {
                "tags": [
                    "redirect-controller"
                ],
                "summary": "privilegeToPrivileges",
                "operationId": "privilegeToPrivilegesUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/role": {
            "get": {
                "tags": [
                    "redirect-controller"
                ],
                "summary": "roleToRoles",
                "operationId": "roleToRolesUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        },
        "/api/user": {
            "get": {
                "tags": [
                    "redirect-controller"
                ],
                "summary": "userToUsers",
                "operationId": "userToUsersUsingGET",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "*/*"
                ],
                "responses": {
                    "200": {
                        "description": "OK"
                    },
                    "401": {
                        "description": "Unauthorized"
                    },
                    "403": {
                        "description": "Forbidden"
                    },
                    "404": {
                        "description": "Not Found"
                    }
                }
            }
        }
    },
    "definitions": {
        "Role": {
            "properties": {
                "description": {
                    "type": "string"
                },
                "id": {
                    "type": "integer",
                    "format": "int64"
                },
                "name": {
                    "type": "string"
                },
                "privileges": {
                    "type": "array",
                    "items": {
                        "$ref": "#/definitions/Privilege"
                    }
                }
            }
        },
        "User": {
            "properties": {
                "email": {
                    "type": "string"
                },
                "id": {
                    "type": "integer",
                    "format": "int64"
                },
                "locked": {
                    "type": "boolean"
                },
                "name": {
                    "type": "string"
                },
                "password": {
                    "type": "string"
                },
                "roles": {
                    "type": "array",
                    "items": {
                        "$ref": "#/definitions/Role"
                    }
                }
            }
        },
        "OAuth2RefreshToken": {
            "properties": {
                "value": {
                    "type": "string"
                }
            }
        },
        "OAuth2AccessToken": {
            "properties": {
                "additionalInformation": {
                    "type": "object"
                },
                "expiration": {
                    "type": "string",
                    "format": "date-time"
                },
                "expired": {
                    "type": "boolean"
                },
                "expiresIn": {
                    "type": "integer",
                    "format": "int32"
                },
                "refreshToken": {
                    "$ref": "#/definitions/OAuth2RefreshToken"
                },
                "scope": {
                    "type": "array",
                    "items": {
                        "type": "string"
                    }
                },
                "tokenType": {
                    "type": "string"
                },
                "value": {
                    "type": "string"
                }
            }
        }
    }
}
```

#### UI Swagger
```
GET http://localhost:8086/um-web/api/swagger-ui.html

API WITH JWT
```

#### Documentation

http://swagger.io/

https://github.com/springfox/springfox

http://springfox.github.io/springfox/docs/current/#introduction