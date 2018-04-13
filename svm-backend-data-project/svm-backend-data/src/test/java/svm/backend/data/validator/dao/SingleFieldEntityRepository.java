package svm.backend.data.validator.dao;

import com.querydsl.core.types.Predicate;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SingleFieldEntityRepository extends PagingAndSortingRepository<SingleFieldEntity, Long>,
        QueryDslPredicateExecutor<SingleFieldEntity> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT"))
    @Override
    public Iterable<SingleFieldEntity> findAll(Predicate predicate);
    
}
