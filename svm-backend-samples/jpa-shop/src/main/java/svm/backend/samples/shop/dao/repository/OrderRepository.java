package svm.backend.samples.shop.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.samples.shop.dao.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    
}
