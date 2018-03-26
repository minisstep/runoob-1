package com.runoob.cloud.consumer.dm.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMController {
    @Autowired
    private ServiceClient serviceClient;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/consumer")
    public String dc() throws InterruptedException {
        Thread.sleep(5000L);
        return serviceClient.consumer();
    }

    public String fallback() {
        return "访问超时啦";
    }
}
