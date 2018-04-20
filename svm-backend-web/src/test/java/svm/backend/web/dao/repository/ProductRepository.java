package svm.backend.web.dao.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.web.dao.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        QueryDslPredicateExecutor<Product> {
    
}
