package svm.backend.data.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MultipleFieldEntityRepository extends PagingAndSortingRepository<MultipleFieldEntity, Long>,
        QueryDslPredicateExecutor<MultipleFieldEntity> {
    
}
