package com.example.codePicasso.global.common;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private Long userId;

    public CustomUser(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.userId = Long.valueOf(username);
    }

    public Long getUserId() {
        return userId;
    }
}
