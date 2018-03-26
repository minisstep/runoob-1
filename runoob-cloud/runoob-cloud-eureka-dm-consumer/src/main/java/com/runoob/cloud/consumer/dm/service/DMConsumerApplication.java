package com.runoob.cloud.consumer.dm.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

//实现断路器
@EnableCircuitBreaker
//负载均衡
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class DMConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DMConsumerApplication.class, args);
    }
}
