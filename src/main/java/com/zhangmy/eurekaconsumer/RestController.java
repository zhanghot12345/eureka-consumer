package com.zhangmy.eurekaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@org.springframework.web.bind.annotation.RestController
public class RestController{

    @Autowired
    private ProducerRemote producerRemote;

    @Autowired
    private HyStrixSevice hyStrixSevice;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findCity")
    public String findCity(@RequestParam String id)
    {
        System.out.println(new FindCityCommand(id, restTemplate).execute());
        return new FindCityCommand(id, restTemplate).execute();
        //return producerRemote.getHello();
    }

    @GetMapping("/get")
    public String index()
    {
        return restTemplate.getForEntity("http://SpringBootRest/hello",String.class).getBody();
        //return producerRemote.getHello();
    }

    @GetMapping("/command")
    public String testHystrixCommand() throws ExecutionException, InterruptedException {
        String syncResult = new IndexCommand(restTemplate).execute();
        String noSyncResult = new IndexCommand(restTemplate).queue().get();
        return syncResult + "    " + noSyncResult;
    }

    @GetMapping("/get1")
    public String hIndex()
    {
        long start = System.currentTimeMillis();
        String index = hyStrixSevice.index();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return index;
    }

    // 异步调用接口
    @GetMapping("/get2")
    public String hIndex1() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        Future<String> future = hyStrixSevice.getIndex();

        while (!future.isDone())
        {
            Thread.sleep(10);
        }
        String index = future.get();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return index;
    }

    @GetMapping("get3")
    public String ObIndex() throws InterruptedException, ExecutionException {
        Thread.sleep(2000l);
        long start = System.currentTimeMillis();

        Observable<String> stringObservable = hyStrixSevice.ObGetIndex();
        Thread.sleep(2000l);
        stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("over");

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(1123);
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
        // 如果需要返回结果可以用Observable.toBlocking().toFuture.get()获取真实结果
//        System.out.println(stringObservable.toBlocking().toFuture().get());

        return "success";
    }
}
