package svm.backend.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("svm.backend.security")
public class SecurityProperties {
    private boolean enableAuthorizationServer = false;
    private boolean enableResourceServer = false;
}
