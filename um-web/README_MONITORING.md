## Rest API Monitoring
### Spring boot actuator
* Dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

* Base endpoints
    * /info
    * /health
    * /metrics
    
* Is possible to configure and tune info provided by the above endpoints

### Actuator endpoint configurations
* Config is done in `application.properties` file
```
# enable or disable /info endpoint
endpoints.info.enabled=true
# to give endpoint another name
endpoints.info.id=
#
endpoints.info.path=
# if the endpoint needs to be secured or not
endpoints.info.sensitive=true

# allow to change context-path of endpoints
management.context-path=/management
# if I don´t use this in false then I receive an 401 Full authentication is required to access this resource
management.security.enabled=false
```

### API

* [http://localhost:8086/um-web/management/health]
```json
{
    "status": "UP"
}
```

* [http://localhost:8086/um-web/management/info]
```json
{
    "app": {
        "name": "Something"
    },
    "build": {
        "version": "0.0.1-SNAPSHOT"
    }
}
```

[http://localhost:8086/um-web/management/metrics]
```json
{
    "mem": 656619,
    "mem.free": 519637,
    "processors": 4,
    "instance.uptime": 19069,
    "uptime": 30476,
    "systemload.average": -1,
    "heap.committed": 576512,
    "heap.init": 131072,
    "heap.used": 56874,
    "heap": 1842688,
    "nonheap.committed": 81560,
    "nonheap.init": 2496,
    "nonheap.used": 80108,
    "nonheap": 0,
    "threads.peak": 32,
    "threads.daemon": 25,
    "threads.totalStarted": 65,
    "threads": 27,
    "classes": 11984,
    "classes.loaded": 11985,
    "classes.unloaded": 1,
    "gc.ps_scavenge.count": 12,
    "gc.ps_scavenge.time": 617,
    "gc.ps_marksweep.count": 3,
    "gc.ps_marksweep.time": 422,
    "httpsessions.max": -1,
    "httpsessions.active": 0
}
```