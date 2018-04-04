package svm.backend.signup.service;

import java.util.UUID;

public class SimpleEmailPasswordGenerator implements EmailPasswordGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
    
}
