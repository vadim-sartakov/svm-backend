package svm.backend.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("svm.backend.web")
public class WebProperties {
    private String homeUrl;
}
