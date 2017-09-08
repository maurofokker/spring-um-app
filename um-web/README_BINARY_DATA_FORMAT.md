### Binary formats with kryo
* dependency
```xml
<dependency>
    <groupId>com.esotericsoftware</groupId>
    <artifactId>kryo</artifactId>
    <version>4.0.0</version>
</dependency>
```
* configuration with Spring HTTP message converters
    -> see implementation in source code
```java
public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    public static final MediaType KRYO = new MediaType("application", "x-kryo");
    public KryoHttpMessageConverter() {
        super(KRYO);
    }
    @Override
    protected boolean supports(final Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }
    @Override
    protected Object readInternal(final Class<? extends Object> clazz, final HttpInputMessage inputMessage) throws IOException {
        return null; // see implementation in source code
    }
    @Override
    protected void writeInternal(final Object object, final HttpOutputMessage outputMessage) throws IOException {
        // see implementation in source code
    }
    @Override
    protected MediaType getDefaultContentType(final Object object) {
        return KRYO;
    }
}

```
* add the Kryo message converter to `HttpMessageConverter` in Web Config using more low level config with `WebMvcConfigurationSupport`

### Example with Kryo 
* request
``` 
GET http://localhost:8086/um-web/api/privileges/1
Headers:
    Accept: application/x-kryo
    Authorization: Bearer <token>
```

* response 

``` 
ROLE_PRIVILEGE_REA�org.hibernate.collection.internal.PersistentSe�OXJUs�ROLE_USE�
ROLE_USER_REA�mDcva�ROLE_ADMI�	ROLE_ROLE_REA�ROLE_PRIVILEGE_WRIT�ROLE_ROLE_WRIT�ROLE_USER_WRIT�
```

### Example with JSON
 * request
 ``` 
 GET http://localhost:8086/um-web/api/privileges/1
 Headers:
     Accept: application/json
     Authorization: Bearer <token>
 ```
 
 * response 
 
 ```json 
 {
     "id": 1,
     "name": "ROLE_PRIVILEGE_READ",
     "description": null
 }
 ```
 
 ### Documentation
 [Binary Data Formats in a Spring REST API](http://www.baeldung.com/spring-rest-api-with-binary-data-formats)
 
 [Http Message Converters with the Spring Framework](http://www.baeldung.com/spring-httpmessageconverter-rest)