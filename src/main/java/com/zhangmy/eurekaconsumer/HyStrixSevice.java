package com.zhangmy.eurekaconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.Future;

@Service
public class HyStrixSevice {

    @Autowired
    private RestTemplate restTemplate;

    @CacheResult
    @HystrixCommand(fallbackMethod = "fallbackIndex",
            commandKey = "index", groupKey = "HyStrixSerivceGroup", threadPoolKey = "indexThread")
    public String index()
    {

        return restTemplate.getForEntity("http://SpringBootRest/hello",String.class).getBody();
    }

    private String fallbackIndex(Throwable throwable)
    {
        throwable.printStackTrace();
        return "error";
    }

    // 异步调用接口
    @HystrixCommand
    public Future<String> getIndex()
    {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {

                return restTemplate.getForEntity("http://SpringBootRest/hello",String.class).getBody();
            }
        };
    }

    // observableExecutionMode = ObservableExecutionMode.LAZY = tobservable()返回clod Observable 订阅后发射
    // observableExecutionMode = ObservableExecutionMode.EAGER = observable()返回hot OBservable 立即发射
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<String> ObGetIndex()
    {
        return Observable.create(new OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    if (!subscriber.isUnsubscribed())
                    {
                        String index = restTemplate.getForObject("http://SpringBootRest/hello",String.class);
                        System.out.println("observable");
                        subscriber.onNext(index);
//                        subscriber.onNext("123");
                        subscriber.onCompleted();
                    }
                }
                catch (Exception e)
                {
                    System.out.println(112);
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io());
    }



}