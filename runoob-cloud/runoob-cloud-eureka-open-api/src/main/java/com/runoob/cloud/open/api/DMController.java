package com.runoob.cloud.open.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMController {
    @Autowired
    private ServiceClient serviceClient;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/query")
    public String query() {
//        Thread.sleep(5000L);
        return serviceClient.service();
    }

    public String fallback() {
        return "访问超时啦";
    }
}
