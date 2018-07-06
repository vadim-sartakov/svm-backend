package svm.backend.data.core.dao.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.core.dao.entity.MultipleFieldEntity;

public interface MultipleFieldEntityRepository extends PagingAndSortingRepository<MultipleFieldEntity, Long>,
        QuerydslPredicateExecutor<MultipleFieldEntity> {
    
}
