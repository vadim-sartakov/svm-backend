package svm.backend.samples.jpa.account.config.migration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationConfiguration {
    @Bean
    public Account_1_0_0 account_1_0_0() {
        return new Account_1_0_0();
    }
}
