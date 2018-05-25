package svm.backend.web.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.web.Application;
import svm.backend.web.dao.entity.Order;
import svm.backend.web.dao.entity.Order.OrderBuilder;
import svm.backend.web.dao.entity.Order.Product;
import svm.backend.web.dao.entity.Order.Product.ProductBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Import(OrderRestController.class)
@Transactional
public class WebExceptionHandlerIT {
   
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    
    private final OrderBuilder orderBuilder = Order.builder();
    private final ProductBuilder productBuilder = Product.builder();
    
    @Test
    public void testRepositoryConstraintViolation() throws Exception {
        sendOrdersSet("/api/orders");
    }
    
    private void sendOrdersSet(String apiPath) throws Exception {
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
    }
    
    private ResultActions expectWrongObject(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Wrong object")));
    }
    
    private ResultActions postOrder(String apiPath) throws Exception {
        return mockMvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderBuilder.build())))
                .andDo(print());
    }
    
    @Test
    public void testControllerConstraintViolation() throws Exception {
        sendOrdersSet("/order-rest-controller");
    }
        
    @Test
    public void testInternalServerError() throws Exception {
        mockMvc.perform(get("/order-rest-controller"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Internal server error")));
    }
    
}
