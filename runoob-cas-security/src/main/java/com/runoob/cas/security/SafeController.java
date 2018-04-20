package com.runoob.cas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2018-04-19 11:33
 */
@RestController
public class SafeController {

    @Autowired
    private TestService testService;

    @GetMapping("/")
    public String safeGet(){
        testService.safeGet();
        return "safeGet";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/safe2")
    public String safePost(){
        return "safePost";
    }

}
