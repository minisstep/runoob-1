package com.runoob.cloud.zuul.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication2.class, args);
    }
}
