package com.zhangmy.eurekaconsumer;

import com.zhangmy.ProducerRemoteHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ProducerRemoteHystrix producerRemoteHystrix()
    {
        return new ProducerRemoteHystrix();
    }
}
