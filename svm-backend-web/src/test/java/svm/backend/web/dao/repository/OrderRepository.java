package svm.backend.web.dao.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.web.dao.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, QueryDslPredicateExecutor<Order> {
    
}
