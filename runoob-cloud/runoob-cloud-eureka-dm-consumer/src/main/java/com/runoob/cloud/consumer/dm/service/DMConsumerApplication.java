package com.runoob.cloud.consumer.dm.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class DMConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DMConsumerApplication.class, args);
    }
}
