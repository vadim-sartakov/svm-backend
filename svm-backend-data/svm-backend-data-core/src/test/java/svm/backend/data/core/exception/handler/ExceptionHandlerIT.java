package svm.backend.data.core.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matcher;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.core.Application;
import svm.backend.data.core.controller.OrderRestController;
import svm.backend.data.core.dao.entity.Order;
import svm.backend.data.core.dao.entity.Order.OrderBuilder;
import svm.backend.data.core.dao.entity.Order.Product.ProductBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(OrderRestController.class)
public class ExceptionHandlerIT {
    
    @LocalServerPort
    private int port;
    
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TestRestTemplate restTemplate;
    
    private String url;
    private OrderBuilder orderBuilder;
    private ProductBuilder productBuilder;
    
    @Before
    public void setUp() {
        url = "http://localhost:" + port;
        orderBuilder = Order.builder();
        productBuilder = Order.Product.builder();
    }
    
    @Test
    public void testRepositoryConstraintViolation() throws Exception {
        sendOrdersSet(url + "/api/orders");
    }
    
    private void sendOrdersSet(String apiPath) throws Exception {
        
        ResponseEntity<String> response;
        
        response = postOrder(apiPath);
        expectWrongObject(response);
        checkJsonPath(response.getBody(), "$.errors.number", is("may not be empty"));
            
        orderBuilder.number("1");
        
        response = postOrder(apiPath);
        expectWrongObject(response);
        checkJsonPath(response.getBody(), "$.errors.products", containsString("size must be between 1 and"));
        
        productBuilder.name("One");
        orderBuilder.product(productBuilder.build());
        
        response = postOrder(apiPath);
        expectWrongObject(response);
        checkJsonPath(response.getBody(), "$.errors.['products[0].quantity']", containsString("may not be null"));
        
        productBuilder.quantity(5);
        orderBuilder.clearProducts();
        orderBuilder.product(productBuilder.build());
        
        response = postOrder(apiPath);
        assertEquals(201, response.getStatusCodeValue());
        
    }
    
    private void expectWrongObject(ResponseEntity<String> response) throws Exception {
        assertEquals(400, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "$.message", is("Wrong object"));
    }
    
    private ResponseEntity<String> postOrder(String apiPath) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(orderBuilder.build()), headers);
        return restTemplate.exchange(apiPath, HttpMethod.POST, requestEntity, String.class);
    }
    
    @Test
    public void testControllerConstraintViolation() throws Exception {
        sendOrdersSet(url + "/order-rest-controller");
    }
    
    @SuppressWarnings("unchecked")
    private void checkJsonPath(String json, String path, Matcher matcher) throws Exception {
        String actualValue = JsonPath.parse(json).read(path, String.class);
        assertThat(actualValue, matcher);
    }
    
}
