package svm.backend.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = { WebMvcAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
