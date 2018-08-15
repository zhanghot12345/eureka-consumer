package com.zhangmy.eurekaconsumer;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;

//@Configuration
public class MyRibbonConfiguration {

    @Bean
    public IPing ribbonPing(IClientConfig iClientConfig)
    {
        return new PingUrl();
    }

    @Bean
    public IClientConfig iClientConfig()
    {
        return new DefaultClientConfigImpl();
    }
}
