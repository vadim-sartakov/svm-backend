package svm.backend.samples.shop.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.samples.shop.dao.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, UUID>,
        QueryDslPredicateExecutor<Order> {
    
}
