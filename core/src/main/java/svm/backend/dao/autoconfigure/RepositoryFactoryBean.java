package svm.backend.dao.autoconfigure;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import svm.backend.dao.repository.GenericRepositoryImpl;
import svm.backend.dao.repository.GenericRepository;

/**
 * Provides ability to define custom base repository implementation.
 * @author Sartakov
 * @param <T>
 * @param <S>
 * @param <ID> 
 */
public class RepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
        extends JpaRepositoryFactoryBean<T, S, ID> {
    
    public RepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new DefaultRepositoryFactory(entityManager);
    }
    
    public static class DefaultRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
        
        public DefaultRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
           return GenericRepository.class;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected SimpleJpaRepository<T, ID> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation entityInformation = getEntityInformation(information.getDomainType());             
            return new GenericRepositoryImpl<>(entityInformation, entityManager);            
        }
        
    }
    
}
