package com.mebr0.security.crypto;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("passwordEncoder")
public class PasswordEncoder {

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
