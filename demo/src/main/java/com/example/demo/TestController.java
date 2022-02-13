package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController("/test")
public class TestController {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 6, 3,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));
    private static ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(5);

    @RequestMapping("/test")
    public String index() {


        scheduledThreadPool.submit(()->{
            System.out.println("test");
        });

        scheduledThreadPool.execute(()->{
            System.out.println("test");
        });
        threadPool.submit(()->{
            System.out.println("test");
        });
        return "Hello World";
    }

}
