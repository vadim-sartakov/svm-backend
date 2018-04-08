package svm.backend.data.validator;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MultipleFieldEntityRepository extends PagingAndSortingRepository<MultipleFieldEntity, Long>,
        QueryDslPredicateExecutor<MultipleFieldEntity> {
    
}
