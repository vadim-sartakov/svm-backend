package svm.backend.data.jpa.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Enables constraint validation provided by spring.
 * @author sartakov
 */
@Configuration
@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
public class HibernateAutoConfiguration extends HibernateJpaAutoConfiguration {
    
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;
    
    public HibernateAutoConfiguration(DataSource dataSource, JpaProperties jpaProperties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
    }
    
    @Override
    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
        super.customizeVendorProperties(vendorProperties);
        validator.setValidationMessageSource(messageSource);
        vendorProperties.put("javax.persistence.validation.factory", validator);
    }
    
}
