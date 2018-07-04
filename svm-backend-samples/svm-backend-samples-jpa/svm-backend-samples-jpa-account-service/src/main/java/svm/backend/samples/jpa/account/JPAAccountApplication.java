package svm.backend.samples.jpa.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class JPAAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(JPAAccountApplication.class, args);
    }
}
