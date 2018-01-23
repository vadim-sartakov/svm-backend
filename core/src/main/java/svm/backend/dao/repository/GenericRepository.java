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
 * Basic repository, inherits essential repositories.
 * Methods with prefix 'super' invoke methods of base class directly. It's useful
 * to override behavior of basic methods (for example, for implementing data security with predicates)
 * while keeping base methods still available. 
 * @author Sartakov
 * @param <T>
 * @param <ID> 
 */
@NoRepositoryBean
@Transactional
public interface GenericRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
        QueryDslPredicateExecutor<T> {
    
    // TODO: make filter prefixes here, like between, startsWith, etc
    
    Page<T> superFindAll(Predicate predicate, Pageable pageable);
    T superFindOne(ID id);
    T superFindOne(Predicate predicate);
    T superSave(T entity);
    void superDelete(ID id);
    void superDelete(T entity);
    void superDeleteAll();
    
}
