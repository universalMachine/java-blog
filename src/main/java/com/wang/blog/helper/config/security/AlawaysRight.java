package com.wang.blog.helper.config.security;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;

public class AlawaysRight implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return null;
    }
}
