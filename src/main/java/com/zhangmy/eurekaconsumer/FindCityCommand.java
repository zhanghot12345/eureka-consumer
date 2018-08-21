package com.zhangmy.eurekaconsumer;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.client.RestTemplate;

public class FindCityCommand extends HystrixCommand<String>{

    private RestTemplate restTemplate;

    private String id;

    protected FindCityCommand(String id, RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FindCityGroup"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FindCityPool")));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("findcity");
        System.out.println(id);
        System.out.println(restTemplate == null);
        return restTemplate.getForObject("http://SpringBootRest/findCity?id={1}",String.class, id);
    }

    @Override
    protected String getCacheKey(){
        return String.valueOf(id);
    }
}
