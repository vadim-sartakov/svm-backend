package svm.backend.data.core.dao.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.core.dao.entity.QSingleFieldEntity;
import svm.backend.data.core.dao.entity.SingleFieldEntity;
import svm.backend.data.core.security.Filter;
import svm.backend.data.dao.entity.QSingleFieldEntity;
import svm.backend.data.dao.entity.SingleFieldEntity;
import svm.backend.data.security.Filter;

public interface SingleFieldEntityRepository extends PagingAndSortingRepository<SingleFieldEntity, Long>,
        QueryDslPredicateExecutor<SingleFieldEntity>, Filter<Long> {

    BooleanExpression PREDICATE = QSingleFieldEntity.singleFieldEntity.uniqueNumber.eq(1);
    
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT"))
    @Override
    public Iterable<SingleFieldEntity> findAll(Predicate predicate);
    
    @Override
    default BooleanExpression getFindOnePredicate(Long id) {
        return QSingleFieldEntity.singleFieldEntity.id.eq(id).and(PREDICATE);
    }
    
    @Override
    default BooleanExpression getFindAllPredicate() {
        return PREDICATE;
    }
    
}
