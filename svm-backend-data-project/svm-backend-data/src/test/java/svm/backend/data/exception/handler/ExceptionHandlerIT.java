package svm.backend.data.exception.handler;

import static org.hamcrest.CoreMatchers.is;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import svm.backend.data.Application;
import svm.backend.data.dao.entity.Order;
import svm.backend.data.dao.entity.Order.OrderBuilder;
import svm.backend.data.dao.entity.Order.Product;
import svm.backend.data.dao.entity.Order.Product.ProductBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionHandlerIT {
    
    @LocalServerPort
    private int port;
    
    private final OrderBuilder orderBuilder = Order.builder();
    private final ProductBuilder productBuilder = Product.builder();
    
    /*@Test
    public void testRepositoryConstraintViolation() throws Exception {
        sendOrdersSet("/api/orders");
    }*/
    
    /*private void sendOrdersSet(String apiPath) throws Exception {
        expectWrongObject(postOrder(apiPath))
                .andExpect(jsonPath("$.errors.number", is("may not be empty")));        
        orderBuilder.number("1");
        expectWrongObject(postOrder(apiPath))
                .andExpect(jsonPath("$.errors.products", containsString("size must be between 1 and")));
        productBuilder.name("One");
        orderBuilder.product(productBuilder.build());
        expectWrongObject(postOrder(apiPath))
                .andExpect(jsonPath("$.errors.['products[0].quantity']", containsString("may not be null")));
        productBuilder.quantity(5);
        orderBuilder.clearProducts();
        orderBuilder.product(productBuilder.build());
        postOrder(apiPath).andExpect(status().isCreated());
    }*/
    
    private ResultActions expectWrongObject(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Wrong object")));
    }
    
    /*private ResultActions postOrder(String apiPath) throws Exception {
        return mockMvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderBuilder.build())))
                .andDo(print());
    }*/
    
    /*@Test
    public void testControllerConstraintViolation() throws Exception {
        sendOrdersSet("/order-rest-controller");
    }*/
    
}
