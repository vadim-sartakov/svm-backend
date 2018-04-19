package svm.backend.samples.shop.controller;

import com.querydsl.core.types.Predicate;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import svm.backend.samples.shop.dao.entity.Order;
import svm.backend.samples.shop.dao.repository.OrderRepository;
import svm.backend.web.controller.RepositoryController;

@RepositoryRestController
@RequestMapping("/orders/custom")
public class OrderController extends RepositoryController<Order, OrderRepository> {
    
    @Autowired private OrderRepository orderRepository;
    
    @GetMapping
    public ResponseEntity<?> getOrders(DefaultedPageable pageable,
            @QuerydslPredicate(root = Order.class) Predicate predicate,
            PersistentEntityResourceAssembler resourceAssembler) {
        return super.get(pageable, predicate, resourceAssembler, orderRepository);
    }
    
    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Resource<Order> order,
            PersistentEntityResourceAssembler resourceAssembler) {
        return super.post(order, resourceAssembler, orderRepository);
    }

    @Override
    protected Class<Order> getManagedType() {
        return Order.class;
    }
    
}
