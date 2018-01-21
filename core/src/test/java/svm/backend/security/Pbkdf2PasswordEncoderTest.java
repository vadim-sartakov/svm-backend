package svm.backend.security;

import org.junit.Test;
import static org.junit.Assert.*;

public class Pbkdf2PasswordEncoderTest {
    
    private final Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
    
    @Test
    public void testEncode() {
        System.out.println("Hash 1 is " + encoder.encode("123456789"));
        System.out.println("Hash 2 is " + encoder.encode("12-@34q56W78E9"));
    }

    @Test
    public void testMatches() {
        
        assertTrue(encoder.matches("123456789", "p7TAkEsgboKj0EwZIiM8zUqRP5ZSsTqT:0ZJc7cATaLrMG3pE90tMimeh"));
        assertFalse(encoder.matches("123453ff46789", "p7TAkEsgboKj0EwZIiM8zUqRP5ZSsTqT:0ZJc7cATaLrMG3pE90tMimeh"));
        assertFalse(encoder.matches("123456789", "FMJEDnWrvJpfXuyjMO2rnN1TzSSWtqmQ:CbKXmtXp6DZJAi0stWD8Sl1p"));
        
        assertTrue(encoder.matches("12-@34q56W78E9", "FMJEDnWrvJpfXuyjMO2rnN1TzSSWtqmQ:CbKXmtXp6DZJAi0stWD8Sl1p"));
        assertFalse(encoder.matches("123453ff46789", "FMJEDnWrvJpfXuyjMO2rnN1TzSSWtqmQ:CbKXmtXp6DZJAi0stWD8Sl1p"));
        assertFalse(encoder.matches("12-@34q56W78E9", "p7TAkEsgboKj0EwZIiM8zUqRP5ZSsTqT:0ZJc7cATaLrMG3pE90tMimeh"));
        
    }
    
}
