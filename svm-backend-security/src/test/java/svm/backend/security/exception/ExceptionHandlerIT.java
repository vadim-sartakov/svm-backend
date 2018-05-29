package svm.backend.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.security.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionHandlerIT {
    
    @LocalServerPort
    private int port;
    private String url;
    
    @Autowired private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        url = "http://localhost:" + port;
    }
    
    @Autowired private TestRestTemplate restTemplate;
        
    @Test
    public void unauthorized() throws Exception {    
        
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic 123");
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(url + "/order-rest-controller/secured", HttpMethod.GET, requestEntity, String.class);
        assertEquals(401, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Wrong username or password");
        
    }
    
    @Test
    public void accessDenied() throws Exception {  
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/order-rest-controller/secured", String.class);
        assertEquals(403, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Access is denied");
    }
    
    private ResponseEntity<String> postJson(String path, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(content, headers);
        return restTemplate.exchange(url + path, HttpMethod.POST, requestEntity, String.class);
    }
    
    private void checkJsonPath(String json, String path, String expectedValue) {
        try {
            String actualValue = objectMapper.readTree(json).at(path).asText();
            assertEquals(expectedValue, actualValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
