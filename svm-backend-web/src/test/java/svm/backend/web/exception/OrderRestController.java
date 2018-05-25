package svm.backend.web.exception;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.web.dao.entity.Order;
import svm.backend.web.dao.repository.OrderRepository;

@RestController
@RequestMapping("/order-rest-controller")
public class OrderRestController {
    
    @Autowired private OrderRepository orderRepository;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@RequestBody @Valid Order order) {
        orderRepository.save(order);
    }
    
    @GetMapping
    public void get() {
        throw new RuntimeException("Test");
    }
    
}
