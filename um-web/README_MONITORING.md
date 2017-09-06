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

### Custom Health Check
* The purpose is to allow us to make our own decision whether or not the API is up and running or is down
    * check of persistence layer and make sure is ok
    * check any sort of external dependency (caching layer)
    * any kind of check that will determine if the system is up
```java
@Component
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        if (check()) {
            return Health.up().build();
        }
        return Health.outOfService().build();
    }

    /**
     * This is the Health logic
     * - still need to build the health object and the check it self
     * - is possible to inject a repository and do a read to see if system is up
     * @return
     */
    private boolean check() {
        return false;
    }
}
```

#### Custom health API response
``` 
GET http://localhost:8086/um-web/management/health
Header:
    Authorization: Bearer <Token>
```

```json
{
    "status": "OUT_OF_SERVICE",
    "healthCheck": {
        "status": "OUT_OF_SERVICE"
    },
    "diskSpace": {
        "status": "UP",
        "total": 540435709952,
        "free": 501820207104,
        "threshold": 10485760
    },
    "db": {
        "status": "UP",
        "database": "H2",
        "hello": 1
    }
}
```

### Metrics
#### Track custom metrics
* Inject CounterService from spring boot actuator to increment the reading of the metrics we're tracking (by key).
* Increment of key "service.privilege.findByName" the syntax is layer.type_of_data_working_on.operation
* The syntax is a string and could be anything but this is a clean way to track data
* This could be done in the controller layer, also in the service layer, is possible to track any sort of metric
* Apply increment in method
```java
    @Override
    public Privilege findByName(final String name) {
        counterService.increment("service.privilege.findByName");
        return getDao().findByName(name);
    }
```

#### Metrics exporter
##### Simple metric exporter
* Simple metrics exporter that export data into an external system
* The external system will be the logger in this case, the data will be exported into a JSON-enabled logger
* There are a lot of systems that can pick up logs and can transport those into metric systems such as
    * Graphite
    * DLA
    * ELK Stack
    * ElasticSearch
    * Logstack
    * Kibana

```java
//@Component
final class MetricsExporter {

    // in this case the logger will be the external system
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BufferMetricReader metricReader; // replace MetricRepository to access the metrics

    @Autowired
    private CounterService counterService; // to reset the metrics

    @Scheduled(fixedRate = 1000 * 30) // runs every 30 seconds pretty close to real time
    public void exportMetrics() {
        metricReader.findAll().forEach(this::log); // iterate over metrics data and logging the data through the logger
    }

    private void log(final Metric<?> m) {
        logger.info("Reporting metric {}={}", m.getName(), m.getValue());
        counterService.reset(m.getName());
    }
}
```

##### Dropwizard metric exporter
* Maven dependency
```xml
<dependency>
    <groupId>io.dropwizard.metrics</groupId>
    <artifactId>metrics-core</artifactId>
</dependency>
```

* Spring boot integrates MetricRegistry from this library in a clean way so all we need to do is to include in the classpath
```java
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

@Component
final class MetricsExporterDropwizard {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MetricRegistry metricRegistry;

    @Scheduled(fixedRate = 1000 * 30) // every 30 second
    public void exportMetrics() {
        final SortedMap<String, Counter> counters = metricRegistry.getCounters(); // counters indicators
        final SortedMap<String, Gauge> gauges = metricRegistry.getGauges(); // metrics indicators
        counters.forEach(this::log);

    }

    private void log(String counterName, final Counter m) {
        logger.info("Reporting metric {}={}", counterName, m.getCount());
    }
}
```