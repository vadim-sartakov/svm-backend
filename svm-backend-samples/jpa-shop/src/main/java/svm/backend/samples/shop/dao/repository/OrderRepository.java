package svm.backend.samples.shop.dao.repository;

import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.samples.shop.dao.entity.Order;
import svm.backend.samples.shop.dao.entity.QOrder;
import svm.backend.web.querydsl.QueryDslDefaultBinder;

@RepositoryRestResource(excerptProjection = Order.Overview.class)
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID>,
        QueryDslPredicateExecutor<Order>, QueryDslDefaultBinder<QOrder> {

    @EntityGraph("overview")
    @Override
    public Page<Order> findAll(Predicate predicate, Pageable pageable);
    
}
