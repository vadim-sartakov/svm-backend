package svm.backend.samples.shop.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import svm.backend.samples.shop.dao.entity.Order;
import svm.backend.samples.shop.dao.repository.OrderRepository;
import svm.backend.web.controller.RepositoryController;

//@RepositoryRestController
//@RequestMapping("/orders")
public class OrderController extends RepositoryController<Order, OrderRepository> {
    
    @Autowired private OrderRepository orderRepository;
    
    //@PostMapping("/custom")
    public ResponseEntity<?> addOrder(@RequestBody @Valid Resource<Order> order,
            PersistentEntityResourceAssembler resourceAssembler) {
        return super.post(order, resourceAssembler, orderRepository);
    }

    @Override
    protected Class<Order> getManagedType() {
        return Order.class;
    }
    
}
