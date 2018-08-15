package com.zhangmy;

import com.zhangmy.eurekaconsumer.ProducerRemote;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ProducerRemoteHystrix implements ProducerRemote {

    @Override
    public String getHello() {
        return "Producer Server 的服务调用失败";
    }

}
