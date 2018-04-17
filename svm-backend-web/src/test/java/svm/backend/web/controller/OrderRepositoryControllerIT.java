package svm.backend.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.web.Application;
import svm.backend.web.dao.entity.Order;
import svm.backend.web.dao.entity.Order.OrderBuilder;
import svm.backend.web.dao.entity.Product;
import svm.backend.web.dao.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Import(OrderRepositoryController.class)
@Transactional
public class OrderRepositoryControllerIT {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private OrderRepository orderRepository;
    
    @Test
    public void testRepositoryGet() throws Exception {
        
        mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
        
        orderRepository.save(createOrder("1").build());
        Long idTwo = orderRepository.save(createOrder("2").build()).getId();
        Long idThree = orderRepository.save(createOrder("3").employee("John").build()).getId();
        
        mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(3)));
        
        mockMvc.perform(get("/api/orders").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        
        mockMvc.perform(get("/api/orders/" + idTwo))
                .andDo(print())
                .andExpect(status().isNotFound());
        
        mockMvc.perform(get("/api/orders/" + idThree))
                .andDo(print())
                .andExpect(status().isOk());
                
    }
    
    private OrderBuilder createOrder(String id) {
        return Order.builder()
                .number(id)
                .product(Product.builder().name("Test").quantity(5).build());
    }
    
    @Test
    public void testRepositoryPostAndDelete() throws Exception {
                
        Order order = createOrder("1").build();
        
        String location = mockMvc.perform(post("/api/orders")
                .content(objectMapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse().getHeader("Location");
        assertThat(location, containsString("/api/orders/"));
        
        // Delete method has to be still available as it wasn't in custom controller
        mockMvc.perform(delete(location))
                .andExpect(status().isNoContent())
                .andDo(print());
        
    }
    
}
