package svm.backend.samples.shop.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import svm.backend.samples.shop.dao.entity.Product;
import svm.backend.samples.shop.dao.entity.QProduct;
import svm.backend.web.querydsl.QueryDslDefaultBinder;

@Secured("ROLE_MANAGER")
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>,
        QueryDslPredicateExecutor<Product>, QueryDslDefaultBinder<QProduct> {
    
}
