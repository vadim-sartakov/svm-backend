package svm.backend.data.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryFactoryAutoConfiguration {
    
    @PersistenceContext private EntityManager entityManager;
    @Autowired private DataSource dataSource;
    
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
