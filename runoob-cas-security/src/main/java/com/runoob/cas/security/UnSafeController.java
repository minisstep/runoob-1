package com.runoob.cas.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2018-04-19 11:33
 */
@RestController
public class UnSafeController {
    @GetMapping("/logout")
    public String unSafeGet() {
        return "logout2";
    }

    @GetMapping("/Access_Denied")
    public String unSafePost() {
        return "Access_Denied";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/xxx")
    public String api() {
        return "xxx";
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/register")
    public String register() {
        return "register";
    }
}
