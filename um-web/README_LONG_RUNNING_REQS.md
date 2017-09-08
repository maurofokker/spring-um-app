### Long running requests
* Client thread is blocked
* Container thread (Servlet thread) is blocked

### Solutions for long running requests
* this make the processing of the request async

#### Callable functional interface
* Client thread still blocked
* Container thread is released and the work is done by another thread

```java
@RequestMapping(value = "/callable", method = RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
public Callable<User> createUserWithCallable(@RequestBody @Valid final User resource) {
    return new Callable<User>() {

        @Override
        public User call() throws Exception {
            return service.createSlow(resource);
        }
    };
}
```
* Break it down when api is hit:
    1. request is picked up by a Servlet container thread
    2. spring MVC will process that callable in a separate thread, because the return value is Callable 
    3. servlet container thread immediately exits and so it’s able to pick up a new request
    4. the new Spring MVC thread does the internal processing
    5. when processing is done, the result is going to be dispatched back to the container to finish and return to the client

#### DeferredResult
* Same as callable
* Is the Spring alternative for Callable

#### @Async
* Truly async implementation
* Client thread is not blocked
* Container thread is not blocked
