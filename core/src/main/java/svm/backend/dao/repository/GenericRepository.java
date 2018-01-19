package svm.backend.dao.repository;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Basic repository. Methods with 'super' prefix invoke base repository methods.
 * It useful for keeping main methods available while overriding default repository methods.
 * @author Sartakov
 * @param <T>
 * @param <ID> 
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
        QueryDslPredicateExecutor<T> {
    
    // TODO: make filter prefixes here, like between, startsWith, etc
    
    T superFindOne(ID id);
    T superFindOne(Predicate predicate);
    T superSave(T entity);
    Page<T> superFindAll(Predicate predicate, Pageable pageable);
    
}
