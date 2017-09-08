package com.maurofokker.um.web.controller;

import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.service.AsyncService;
import com.maurofokker.um.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

@Controller
@RequestMapping(value = "long/users")
public class AsyncController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AsyncService asyncService;

    // callable

    @RequestMapping(value = "/callable", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Callable<User> createUserWithCallable(@RequestBody final User resource) {
        return new Callable<User>() {
            @Override
            public User call() throws Exception {
                return userService.createSlow(resource);
            }
        };
    }

    // deferred result

    @RequestMapping(value = "/deferred", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<User> createUserWithDeferredResult(@RequestBody final User resource) {
        final DeferredResult<User> result = new DeferredResult<User>();
        asyncService.scheduleCreateUser(resource, result);
        return result;
    }

    @RequestMapping(value = "/deferredComplex", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<User> createUserWithDeferredResultWithAsyncResultSetting(@RequestBody final User resource) {
        final DeferredResult<User> result = new DeferredResult<User>();
        asyncService.scheduleCreateUserWithAsyncResultSetting(resource, result);
        return result;
    }

    // async

    @RequestMapping(value = "/async", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUserWithAsync(@RequestBody final User resource, HttpServletResponse response, UriComponentsBuilder uriBuilder) throws InterruptedException {
        asyncService.createUserAsync(resource);
        final String location = uriBuilder.path("/long").path("/users/").build().encode().toString();        
        response.setHeader("Location", location + resource.getName());
    }

    // find by name

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public User findByName(@PathVariable("name") final String name, HttpServletResponse response) {
        final User user = userService.findByName(name);
        System.out.println(user);
        if (user == null) {
            response.setStatus(404);
        } else if (user.getStatus().equals("In Progress")) {
            response.setStatus(202);
            return null;
        }
        return user;
    }

    //

}
