package com.zhangmy.eurekaconsumer;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/findCity")
public class HystrixContextFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request,response);
        }
        finally {
            hystrixRequestContext.shutdown();
        }
    }

    @Override
    public void destroy() {

    }
}
