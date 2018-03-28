package com.runoob.cloud.provider.dm2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DMProviderApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(DMProviderApplication2.class, args);
    }
}
