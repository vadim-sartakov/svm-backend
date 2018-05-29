package svm.backend.web.exception;

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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.web.Application;
import svm.backend.web.controller.TestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestController.class)
public class CustomErrorAttributesIT {
   
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;
    
    @LocalServerPort
    int port;
    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:" + port;
    }
        
    @Test
    public void wrongRequest() throws Exception {        
        ResponseEntity<String> response = postJson("/test", null);
        assertEquals(400, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Wrong request");
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
    
    @Test
    public void testNotFoundError() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/non-existent", String.class);
        assertEquals(404, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Wrong request");
    }
    
    @Test
    public void testInternalServerError() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/test/error", String.class);
        assertEquals(500, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Internal server error");
    }
        
}
