package svm.backend.data.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import svm.backend.data.annotation.PredefinedProcessor;
import svm.backend.data.jpa.migration.JpaMigrationRepository;
import svm.backend.data.jpa.service.JpaPredefinedProcessor;
import svm.backend.data.migration.service.MigrationRepository;

@Configuration
@PropertySource("classpath:properties/data/jpa/application.properties")
@EntityScan({ "svm.backend.data.jpa.migration", "svm.backend.data.jpa.entity" })
@Import(HibernateConfiguration.class)
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
