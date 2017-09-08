package com.maurofokker.um.service;

import com.maurofokker.um.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    @Autowired
    private IUserService userService;

    public static final long DELAY = 10000L;

    public void scheduleCreateUser(User resource, DeferredResult<User> deferredResult) {
        /**
         * supllyAsync() to run createSlow() operation asynchronously
         * whenCompleteAsync() to passing in the action when the processing finishes
         * when processing finishes set result in DeferredResult
         * info this is the extra control that gives DeferredResult
         */
        CompletableFuture.supplyAsync(() -> userService.createSlow(resource)).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
    }

    /**
     * First set status to in progress and create the resource
     * Resource is created before Sleep bc we want client to know request is being processing and not get a 404 SC
     * Long running processing is done by Thread.sleep 10 seconds in this example
     * After long running is finish set status to ready and then update resource in persistence
     */
    @Async
    public Future<User> createUserAsync(User resource) throws InterruptedException {
        resource.setStatus("In Progress");

        final User result = userService.create(resource);
        Thread.sleep(AsyncService.DELAY);

        result.setStatus("Ready");

        userService.update(result);
        return new AsyncResult<User>(result);
    }
}
