package svm.backend.sms.mts.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.sms.SmsSender;
import svm.backend.sms.mts.MtsSmsSender;

@Configuration
@EnableConfigurationProperties(MtsProperties.class)
public class MtsAutoConfiguration {
    @Bean
    public SmsSender mtsSmsSender() {
        return new MtsSmsSender();
    }
}
