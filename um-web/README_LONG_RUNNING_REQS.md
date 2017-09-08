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
* Is the Spring alternative for Callable
* Same as Callable in terms that client thread is still blocked and servlet container thread not
* But it gives more control in setting the value of the deferred result
* Full control over when and how processing will happen
* Gives more flexibility as to when we can call the set result on our DeferredResult and how we can
* handle all of that processing
* controller to handle deferred result implementation
```java
@RequestMapping(value = "/deferred", method = RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
@ResponseBody
public DeferredResult<User> createUserWithDeferredResult(@RequestBody final User resource) {
    final DeferredResult<User> result = new DeferredResult<User>();
    asyncService.scheduleCreateUser(resource, result);
    return result;
}
```
* Break it down api hit:
    1. create our deferred result
    2. pass that into the service
    3. return it - so that the container thread can exit right away

* Service that control processing
```java
public void scheduleCreateUser(User resource, DeferredResult<User> deferredResult) {
    /**
     * supllyAsync() to run createSlow() operation asynchronously
     * whenCompleteAsync() to passing in the action when the processing finishes
     * when processing finishes set result in DeferredResult
     * info this is the extra control that gives DeferredResult
     */
    CompletableFuture.supplyAsync(() -> userService.createSlow(resource)).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
}
```

#### @Async
* Truly async implementation
* Client thread is not blocked
* Container thread is not blocked
