package com.runoob.cloud.consumer.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMController {
    @Autowired
    private ServiceClient serviceClient;

    @GetMapping("/consumer")
    public String dc() {
        return serviceClient.consumer();
    }
}
