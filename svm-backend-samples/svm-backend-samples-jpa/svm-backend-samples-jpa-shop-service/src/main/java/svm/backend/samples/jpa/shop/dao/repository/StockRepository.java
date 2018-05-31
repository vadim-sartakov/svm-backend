package svm.backend.samples.jpa.shop.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.samples.jpa.shop.dao.entity.QStock;
import svm.backend.samples.jpa.shop.dao.entity.Stock;
import svm.backend.data.querydsl.QueryDslDefaultBinder;

@RepositoryRestResource(excerptProjection = Stock.Overview.class)
public interface StockRepository extends PagingAndSortingRepository<Stock, UUID>,
        QueryDslPredicateExecutor<Stock>, QueryDslDefaultBinder<QStock> {
    
}
