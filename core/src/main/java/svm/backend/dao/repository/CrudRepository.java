package svm.backend.dao.repository;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basic CRUD repository. Contains only method invoked directly from controllers.
 * It's also convenient to implement secured repository by placing related annotation on derived interface.
 * @author Sartakov
 * @param <T>
 * @param <ID> 
 */
@NoRepositoryBean
@Transactional
public interface CrudRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
        QueryDslPredicateExecutor<T> {
    
    @Override
    Page<T> findAll(Predicate predicate, Pageable pageable);
    
    @Override
    T findOne(ID id);
    
    @Override
    T findOne(Predicate predicate);
    
    @Override
    <S extends T> S save(S entity);
    
    @Override
    void delete(ID id);
    
    @Override
    void delete(T entity);
    
    @Override
    void deleteAll();
    
}
