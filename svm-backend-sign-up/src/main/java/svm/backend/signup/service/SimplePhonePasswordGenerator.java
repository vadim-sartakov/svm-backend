package svm.backend.signup.service;

import java.security.SecureRandom;

public class SimplePhonePasswordGenerator implements PhonePasswordGenerator {
    
    private final SecureRandom random = new SecureRandom();
    private final int length;

    public SimplePhonePasswordGenerator() {
        this.length = 4;
    }
    
    public SimplePhonePasswordGenerator(int length) {
        this.length = length;
    }
    
    @Override
    public String generate() {
        return String.format("%0" + length + "d", random.nextInt((int) Math.pow(10, length)));
    }
    
}
