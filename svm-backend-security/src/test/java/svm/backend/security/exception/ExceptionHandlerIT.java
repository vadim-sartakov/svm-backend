package svm.backend.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.security.Application;
import svm.backend.security.config.TestResourceServerConfiguration;
import svm.backend.security.controller.TestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ TestController.class, TestResourceServerConfiguration.class })
public class ExceptionHandlerIT {
    
    @MockBean(name = "daoClientDetailsService") private ClientDetailsService clientDetailsService;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean private UserDetailsService userDetailsService;
    
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
    public void accessPublic() throws Exception {  
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/controller/public", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    @Test
    public void shouldRejectSecured() throws Exception {  
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/controller/secured", String.class);
        assertEquals(401, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Authorization is required");
    }
    
    @Test
    public void shouldSucceedSecured() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url + "/api/controller/secured", HttpMethod.GET, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    private String getAccessToken() throws Exception {
        String response = restTemplate.postForObject(url + "/oauth/token{?grant_type,username,password}",
                null,
                String.class,
                "password",
                "user",
                "secret"
        );
        return objectMapper.readTree(response).at("/access_token").asText();
    }
    
    /*@Test
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
    }*/
        
    private void checkJsonPath(String json, String path, String expectedValue) throws Exception {
        String actualValue = objectMapper.readTree(json).at(path).asText();
        assertEquals(expectedValue, actualValue);
    }
    
}
