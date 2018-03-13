package svm.backend.data.config;

import java.util.Map;
import javax.sql.DataSource;
import javax.validation.Validator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Enables constraint validation provided by spring.
 * @author sartakov
 */
@Configuration
@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
public class HibernateConfig extends HibernateJpaAutoConfiguration {
    
    @Autowired private Validator validator;
    
    public HibernateConfig(DataSource dataSource, JpaProperties jpaProperties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
    }
    
    @Override
    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
        super.customizeVendorProperties(vendorProperties);
        vendorProperties.put("javax.persistence.validation.factory", validator);
    }
    
}
