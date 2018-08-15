package com.zhangmy.eurekaconsumer;

import com.zhangmy.ProducerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "SpringBootRest", fallback = ProducerRemoteHystrix.class)
public interface  ProducerRemote {
    @GetMapping("/hello")
    public String getHello();
}
