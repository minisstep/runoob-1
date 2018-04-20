package com.runoob.cas.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public  class AjaxRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return false;
    }
}