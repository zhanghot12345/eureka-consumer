package com.zhangmy.eurekaconsumer;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.client.RestTemplate;

public class IndexCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;

    protected IndexCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HyStrixSerivceGroup")).andCommandKey(HystrixCommandKey.Factory.asKey("index"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("indexThread")));
        this.restTemplate = restTemplate;

    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://SpringBootRest/hello",String.class);
    }
}
