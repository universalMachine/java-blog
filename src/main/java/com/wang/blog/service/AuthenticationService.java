package com.wang.blog.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wang.blog.helper.config.security.IAuthenticationFacade;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationFacade{

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public  String getUserName() {return getAuthentication().getName();}

    public Boolean isLogined() {
        Authentication authentication = getAuthentication();
        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);}
}
