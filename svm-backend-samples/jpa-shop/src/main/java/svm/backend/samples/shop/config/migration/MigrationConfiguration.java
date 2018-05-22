package svm.backend.samples.shop.config.migration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MigrationConfiguration {
    @Bean
    public Shop_1_0_0 shop_1_0_0() {
        return new Shop_1_0_0();
    }
}
