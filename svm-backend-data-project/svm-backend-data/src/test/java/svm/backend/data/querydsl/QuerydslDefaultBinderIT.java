package svm.backend.data.querydsl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.Application;
import svm.backend.data.dao.entity.Order;
import svm.backend.data.dao.entity.Order.Product;
import svm.backend.data.dao.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Transactional
public class QuerydslDefaultBinderIT {
    
    @Autowired private OrderRepository orderRepository;
    @Autowired private MockMvc mockMvc;
    
    private final ZonedDateTime NOW = ZonedDateTime.now();
    
    @Before
    public void setUp() {
        orderRepository.save(createOrder(1, NOW.minusDays(1)));
        orderRepository.save(createOrder(5, NOW));
        orderRepository.save(createOrder(20, NOW.plusDays(1)));
        orderRepository.save(createOrder(35, NOW.plusDays(2)));
    }
    
    private Order createOrder(int code, ZonedDateTime date) {
        return Order.builder()
                .date(date)
                .number(Integer.toString(code) + "/qrz-5")
                .code(code)
                .product(Product.builder().name("Test").quantity(1).build())
                .build();
    }
    
    @Test
    public void testDates() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("date", NOW.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        mockMvc.perform(get("/api/orders")
                .param("date", NOW.minusDays(5).format(DateTimeFormatter.ISO_DATE_TIME))
                .param("date", NOW.plusHours(1).format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.page.totalElements", is(2)));
        mockMvc.perform(get("/api/orders")
                .param("date", NOW.minusDays(1).format(DateTimeFormatter.ISO_DATE_TIME))
                .param("date", NOW.format(DateTimeFormatter.ISO_DATE_TIME))
                .param("date", NOW.plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.page.totalElements", is(3)));
    }
    
    @Test
    public void testNumbers() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("code", "5"))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        mockMvc.perform(get("/api/orders")
                .param("code", "0")
                .param("code", "7"))
                .andExpect(jsonPath("$.page.totalElements", is(2)));
        mockMvc.perform(get("/api/orders")
                .param("code", "1")
                .param("code", "5")
                .param("code", "20"))
                .andExpect(jsonPath("$.page.totalElements", is(3)));
    }
    
    @Test
    public void testStrings() throws Exception {
        mockMvc.perform(get("/api/orders")
                .param("number", "1"))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        mockMvc.perform(get("/api/orders")
                .param("number", "qrz"))
                .andExpect(jsonPath("$.page.totalElements", is(4)));
        mockMvc.perform(get("/api/orders")
                .param("number", "1/qrz-5")
                .param("number", "5/qrz-5")
                .param("number", "20/qrz-5"))
                .andExpect(jsonPath("$.page.totalElements", is(3)));
    }
    
}
