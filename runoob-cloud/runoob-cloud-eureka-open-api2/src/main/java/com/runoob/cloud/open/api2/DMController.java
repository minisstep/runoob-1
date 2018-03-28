package com.runoob.cloud.open.api2;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMController {
    @Autowired
    private ServiceClient serviceClient;
    @Value("${service.name.config}")
    private String config;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/query")
    public String query() {
        return serviceClient.service() + " profile:" + config;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/test")
    public String test() {
//        Thread.sleep(5000L);
        return serviceClient.service();
    }

    public String fallback() {
        return "访问超时啦";
    }
}
