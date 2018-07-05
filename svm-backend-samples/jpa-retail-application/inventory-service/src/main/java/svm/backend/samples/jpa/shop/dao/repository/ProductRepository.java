package svm.backend.samples.jpa.shop.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import svm.backend.samples.jpa.shop.dao.entity.Product;
import svm.backend.samples.jpa.shop.dao.entity.QProduct;
import svm.backend.data.core.querydsl.QueryDslDefaultBinder;

@Secured("ROLE_MANAGER")
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>,
        QueryDslPredicateExecutor<Product>, QueryDslDefaultBinder<QProduct> {
    
}
