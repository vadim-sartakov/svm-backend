package svm.backend.web.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(OrderRestController.class)
@Transactional
public class CustomErrorAttributesIT {
   
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;
    
    @LocalServerPort
    int port;
    
    private String url;
    
    private final OrderBuilder orderBuilder = Order.builder();
    private final ProductBuilder productBuilder = Product.builder();
    
    @Before
    public void setUp() {
        url = "http://localhost:" + port;
    }
    
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
        
    @Test
    public void wrongRequest() throws Exception {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url + "/order-rest-controller", HttpMethod.POST, requestEntity, String.class);
        String actualMessage = objectMapper.readTree(response.getBody()).at("/message").asText();
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Wrong object", actualMessage);
        
    }
    
    @Test
    public void testNotFoundError() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/non-existent", String.class);
        String actualMessage = objectMapper.readTree(response.getBody()).at("/message").asText();
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", actualMessage);
    }
    
    @Test
    public void methodNotAllowed() throws Exception {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<?> requestEntity = new HttpEntity<>("", headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url + "/order-rest-controller", HttpMethod.POST, requestEntity, String.class);
        String actualMessage = objectMapper.readTree(response.getBody()).at("/message").asText();
        assertEquals(405, response.getStatusCodeValue());
        assertEquals("Method not allowed", actualMessage);
        
    }
    
    @Test
    public void methodUnsupportedMediaType() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(url + "/order-rest-controller", null, String.class);
        String actualMessage = objectMapper.readTree(response.getBody()).at("/message").asText();
        assertEquals(415, response.getStatusCodeValue());
        assertEquals("Unsupported media type", actualMessage);
    }
    
    @Test
    public void testInternalServerError() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/order-rest-controller", String.class);
        String actualMessage = objectMapper.readTree(response.getBody()).at("/message").asText();
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal server error", actualMessage);
    }
        
}
