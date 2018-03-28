package com.runoob.cloud.open.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("dm-provider")
public interface ServiceClient {
    @GetMapping("/service")
    String service();
}
