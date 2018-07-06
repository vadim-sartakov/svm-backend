package svm.backend.samples.jpa.shop.dao.repository;

import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.samples.jpa.shop.dao.entity.Order;
import svm.backend.samples.jpa.shop.dao.entity.QOrder;
import svm.backend.data.core.querydsl.QueryDslDefaultBinder;

@RepositoryRestResource(excerptProjection = Order.Overview.class)
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID>,
        QuerydslPredicateExecutor<Order>, QueryDslDefaultBinder<QOrder> {

    @EntityGraph("OrderOverview")
    @Override
    Page<Order> findAll(Predicate predicate, Pageable pageable);
    
}
