package svm.backend.web.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import svm.backend.web.dao.entity.Order;
import svm.backend.web.dao.entity.QOrder;
import svm.backend.web.dao.repository.OrderRepository;

@RepositoryRestController
@RequestMapping("/orders")
public class OrderRepositoryController extends RepositoryController<Order, OrderRepository> {
    
    @Autowired private OrderRepository orderRepository;
    
    @GetMapping
    public ResponseEntity<?> get(
            DefaultedPageable pageable,
            @QuerydslPredicate(root = Order.class) Predicate predicate,
            PersistentEntityResourceAssembler entityResourceAssembler) {
        return get(pageable, predicate, entityResourceAssembler, orderRepository);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id,
            PersistentEntityResourceAssembler resourceAssembler) {
        return getOne(QOrder.order.id.eq(id).and(QOrder.order.employee.eq("John")), resourceAssembler, orderRepository);
    }
    
    @PostMapping
    public ResponseEntity<ResourceSupport> post(@RequestBody Resource<Order> order,
            PersistentEntityResourceAssembler resourceAssembler) {
        return post(order, resourceAssembler, orderRepository);
    }
    
    @Override
    protected Class<Order> getManagedType() {
        return Order.class;
    }
    
}
