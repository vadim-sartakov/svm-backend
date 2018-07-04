package svm.backend.data.core.dao.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.core.dao.entity.MultipleFieldEntity;
import svm.backend.data.dao.entity.MultipleFieldEntity;

public interface MultipleFieldEntityRepository extends PagingAndSortingRepository<MultipleFieldEntity, Long>,
        QueryDslPredicateExecutor<MultipleFieldEntity> {
    
}
