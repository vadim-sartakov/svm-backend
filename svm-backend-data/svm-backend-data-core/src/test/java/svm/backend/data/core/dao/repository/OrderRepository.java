package svm.backend.data.core.dao.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.core.dao.entity.Order;
import svm.backend.data.core.dao.entity.QOrder;
import svm.backend.data.core.querydsl.QueryDslDefaultBinder;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>,
        QuerydslPredicateExecutor<Order>,
        QueryDslDefaultBinder<QOrder> {
    
}
