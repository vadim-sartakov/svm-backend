package svm.backend.data.dao.repository;

import svm.backend.data.dao.entity.Order;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.data.dao.entity.QOrder;
import svm.backend.data.querydsl.QueryDslDefaultBinder;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>,
        QueryDslPredicateExecutor<Order>,
        QueryDslDefaultBinder<QOrder>{
    
}
