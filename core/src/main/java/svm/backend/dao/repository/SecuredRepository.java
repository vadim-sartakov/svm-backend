package svm.backend.dao.repository;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.dao.entity.UserRole;

/**
 * Secured implementation of generic repository. If repository secured
 * on interface level, then no further configuration is possible in derived repositories.
 * This interface includes basic repository methods which is already secured.
 * It is possible to loose security restriction on any method,
 * without need to define all of them manually.
 * @author Sartakov
 * @param <T>
 * @param <ID> 
 */
@NoRepositoryBean
@Transactional
public interface SecuredRepository<T, ID extends Serializable> extends GenericRepository<T, ID> {
    
    @Secured(UserRole.ADMIN)
    @Override
    Page<T> findAll(Predicate predicate, Pageable pageable);
    
    @Secured(UserRole.ADMIN)
    @Override
    T findOne(ID id);
    
    @Secured(UserRole.ADMIN)
    @Override
    T findOne(Predicate predicate);
       
    @Secured(UserRole.ADMIN)
    @Override
    <S extends T> S save(S entity);
    
    @Secured(UserRole.ADMIN)
    @Override
    void delete(ID id);
    
    @Secured(UserRole.ADMIN)
    @Override
    void delete(T entity);
    
    @Secured(UserRole.ADMIN)
    @Override
    void deleteAll();
    
}
