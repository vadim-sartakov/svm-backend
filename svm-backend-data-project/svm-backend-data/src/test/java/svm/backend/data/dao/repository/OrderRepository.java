package svm.backend.data.dao.repository;

import svm.backend.data.dao.entity.Order;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>,
        QueryDslPredicateExecutor<Order> {
    
}
