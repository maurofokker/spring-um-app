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

### Documentation
[http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready]
[https://spring.io/guides/gs/actuator-service/]
[http://www.baeldung.com/spring-boot-actuators]


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

* see custom metric `service.privilege.findByName`
```
GET http://localhost:8086/um-web/management/metrics
HEaders:
    Authorization: Bearer <token_
```
```json
{
    "mem": 607885,
    "mem.free": 453985,
    "processors": 4,
    "instance.uptime": 32013,
    "uptime": 46002,
    "systemload.average": -1,
    "heap.committed": 526848,
    "heap.init": 131072,
    "heap.used": 72862,
    "heap": 1842688,
    "nonheap.committed": 82520,
    "nonheap.init": 2496,
    "nonheap.used": 81042,
    "nonheap": 0,
    "threads.peak": 33,
    "threads.daemon": 28,
    "threads.totalStarted": 63,
    "threads": 31,
    "classes": 12112,
    "classes.loaded": 12112,
    "classes.unloaded": 0,
    "gc.ps_scavenge.count": 13,
    "gc.ps_scavenge.time": 350,
    "gc.ps_marksweep.count": 3,
    "gc.ps_marksweep.time": 477,
    "counter.status.503.management.health": 1,
    "gauge.response.management.health": 33,
    "counter.service.privilege.findByName": 12,
    "counter.status.200.oauth.token": 1,
    "gauge.response.oauth.token": 674,
    "httpsessions.max": -1,
    "httpsessions.active": 0
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
#### Documentation
[http://www.baeldung.com/spring-boot-actuators]
[http://kielczewski.eu/2015/01/application-metrics-with-spring-boot-actuator/]
[https://dropwizard.github.io/metrics/3.1.0/]

### Monitoring data over JMX
* allows to see low level app information
* can see where bottlenecks are when performing jmeter tests

#### Using Java VisualVM
* run from .../Java/JDK.1.8_xxx/bin/jvisualvm
* use plugins from https://visualvm.github.io/pluginscenters.html
* download and install [https://visualvm.github.io/archive/downloads/release136/com-sun-tools-visualvm-modules-mbeans_1.nbm]
* connect visualvm to tomcat app running or another server

#### Monitoring a remote Tomcat server
* this should be done on server where tomcat is installed
* add the JMX listener to the Tomcat config:
``` 
sudo vim $TOMCAT_INSTALL_DIR/tomcat/conf/server.xml
```
```xml
<Listener className="org.apache.catalina.mbeans.JmxRemoteLifecycleListener" rmiRegistryPortPlatform="10001" rmiServerPortPlatform="10002"/>
```
* download the JMX jar into the Tomcat lib directory:
```
cd $TOMCAT_INSTALL_DIR/tomcat/lib
wget http://apache.javapipe.com/tomcat/tomcat-8/v8.0.30/bin/extras/catalina-jmx-remote.jar
sudo chmod a+rwx catalina-jmx-remote.jar
```

* Configure the runtime
```
export CATALINA_OPTS="-Xms512m -Xmx1024m -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=<dns_host>"
```

* configurations in VisualVM to monitor remote tomcat
    * add a remote connection, with just the host (dns_host)
    * add the full JMX connection:
    ```
    service:jmx:rmi://dns_host:10002/jndi/rmi://dns_host:10001/jmxrmi
    ```

#### Documentation
[https://visualvm.java.net/]
[https://visualvm.java.net/mbeans_tab.html]
[https://visualvm.java.net/jmx_connections.html]
[https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-jmx.html]

### Controller to expose metrics data over http
* create a controller
* use the already available spring boot data in service
* this metric will conflict with MetricsExporterDropwizard (should comment this class and maven dependency)
* implementation of controller metric is in package `com.maurofokker.common.metric`
* use of api
``` 
GET http://localhost:8086/um-web/metric-graph-data
Headers:
    Authorizarion: Bearer <token>
```

```json
[
    [
        "Time",
        "200"
    ],
    [
        "2017-09-06 15:07",
        0
    ],
    [
        "2017-09-06 15:08",
        3
    ],
    [
        "2017-09-06 15:09",
        0
    ]
]
```