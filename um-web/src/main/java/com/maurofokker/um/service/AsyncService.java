package com.maurofokker.um.service;

import com.maurofokker.um.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

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
}
