package svm.backend.signup.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SimpleEmailPasswordGenerator implements EmailPasswordGenerator {

    @Autowired private PasswordEncoder encoder;
    
    @Override
    public String generate() {
        return encoder.encode(UUID.randomUUID().toString());
    }
    
}
