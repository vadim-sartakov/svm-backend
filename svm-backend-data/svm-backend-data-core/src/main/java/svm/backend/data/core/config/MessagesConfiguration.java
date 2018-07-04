package svm.backend.data.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.core.service.MessagesBasenameProvider;

@Configuration
public class MessagesConfiguration {
    @Bean
    public MessagesBasenameProvider dataMessagesBasenameProvider() {
        return () -> "classpath:locale/data/messages";
    }
}
