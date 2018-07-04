package svm.backend.data.jpa.core.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import svm.backend.data.core.annotation.PredefinedProcessor;
import svm.backend.data.core.migration.service.MigrationRepository;
import svm.backend.data.jpa.core.migration.JpaMigrationRepository;
import svm.backend.data.jpa.core.service.JpaPredefinedProcessor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:properties/data/jpa/application.properties")
@EntityScan({ "svm.backend.data.jpa.core.migration", "svm.backend.data.jpa.core.entity" })
public class DataJpaAutoConfiguration {
    
    @PersistenceContext private EntityManager entityManager;
    @Autowired private DataSource dataSource;
        
    @Bean
    public MigrationRepository migrationRepository() {
        return new JpaMigrationRepository();
    }
    
    @Bean
    public PredefinedProcessor predefinedProcessor() {
        return new JpaPredefinedProcessor();
    }
    
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
    @Bean
    @ConditionalOnBean(com.querydsl.sql.Configuration.class)
    public SQLQueryFactory sqlQueryFactory(com.querydsl.sql.Configuration configuration) {
        return new SQLQueryFactory(configuration, dataSource);
    }
        
}
