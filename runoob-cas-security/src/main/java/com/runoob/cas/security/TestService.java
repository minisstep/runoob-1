package com.runoob.cas.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2018-04-20 13:19
 */
@Service
public class TestService {

    @PreAuthorize("hasRole('ROLE_USER')")
    public String safeGet(){
        return "safeGet";
    }
}
