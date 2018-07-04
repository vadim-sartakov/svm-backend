package svm.backend.data.core.dao.repository;

import svm.backend.data.core.dao.entity.Order;
import svm.backend.data.core.querydsl.QueryDslDefaultBinder;
import svm.backend.data.dao.entity.Order;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.dao.entity.QOrder;
import svm.backend.data.querydsl.QueryDslDefaultBinder;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>,
        QueryDslPredicateExecutor<Order>,
        QueryDslDefaultBinder<QOrder> {
    
}
