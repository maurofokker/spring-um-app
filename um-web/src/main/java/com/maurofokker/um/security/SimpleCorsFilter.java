package com.maurofokker.um.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class SimpleCorsFilter implements Filter {
    public SimpleCorsFilter() {
        super();
    }

    //

    @Override
    public final void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version");
        response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type");

        final HttpServletRequest request = (HttpServletRequest) req;
        if (request.getMethod() != "OPTIONS") {
            chain.doFilter(req, res);
        } else {
            //
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        //
    }

    @Override
    public void destroy() {
        //
    }
}
