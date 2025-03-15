package com.example.codePicasso.global.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String password) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, password.toCharArray());
    }

    public boolean matches(String password, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), encodedPassword);

        return result.verified;
    }
}
