package svm.backend.samples.shop.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.samples.shop.dao.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>,
        QueryDslPredicateExecutor<Product> {
    
}
