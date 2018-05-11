package svm.backend.data.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.security.Filter;

public interface SingleFieldEntityRepository extends PagingAndSortingRepository<SingleFieldEntity, Long>,
        QueryDslPredicateExecutor<SingleFieldEntity>, Filter {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT"))
    @Override
    public Iterable<SingleFieldEntity> findAll(Predicate predicate);
    
    @Override
    default BooleanExpression getPredicate() {
        return QSingleFieldEntity.singleFieldEntity.uniqueNumber.eq(1);
    }
    
}
