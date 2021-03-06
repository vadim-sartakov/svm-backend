package svm.backend.data.jpa.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import svm.backend.data.jpa.core.config.TestConfiguration;

@Configuration
@EnableAutoConfiguration
@Import(TestConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
