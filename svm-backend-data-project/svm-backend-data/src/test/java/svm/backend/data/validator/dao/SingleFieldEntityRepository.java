package svm.backend.data.validator.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SingleFieldEntityRepository extends PagingAndSortingRepository<SingleFieldEntity, Long>,
        QueryDslPredicateExecutor<SingleFieldEntity> {
    
}