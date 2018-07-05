package svm.backend.data.mongo.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import svm.backend.data.mongo.core.config.TestConfiguration;

@Configuration
@EnableAutoConfiguration
@Import(TestConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
