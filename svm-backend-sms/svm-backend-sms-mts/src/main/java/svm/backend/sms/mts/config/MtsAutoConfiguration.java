package svm.backend.sms.mts.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.sms.core.SmsSender;
import svm.backend.sms.mts.MtsSmsSender;

@Configuration
@EnableConfigurationProperties(MtsProperties.class)
public class MtsAutoConfiguration {
    @Bean
    public SmsSender mtsSmsSender(RestTemplateBuilder restTemplateBuilder, MtsProperties properties) {
        return new MtsSmsSender(restTemplateBuilder, properties);
    }
}
