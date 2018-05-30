package svm.backend.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.security.Application;
import svm.backend.security.config.TestResourceServerConfiguration;
import svm.backend.security.controller.TestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ TestController.class, TestResourceServerConfiguration.class })
public class ExceptionHandlerIT {
    
    @Autowired private PasswordEncoder passwordEncoder;
    @MockBean(name = "daoClientDetailsService") private ClientDetailsService clientDetailsService;
    @MockBean private UserDetailsService userDetailsService;
    
    @LocalServerPort
    private int port;
    private String url;
    
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        
        url = "http://localhost:" + port;
        
        // prevents client exceptions on 401 error
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
                
        ClientDetails clientDetails = new BaseClientDetails("client", null, "default", "password", "ROLE_CLIENT");
        User user = new User("user", passwordEncoder.encode("secret"), AuthorityUtils.createAuthorityList("ROLE_USER"));
        User admin = new User("admin", passwordEncoder.encode("secret"), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        
        Mockito.when(clientDetailsService.loadClientByClientId(anyString()))
                .thenReturn(clientDetails);
        Mockito.when(userDetailsService.loadUserByUsername("user"))
                .thenReturn(user);
        Mockito.when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(admin);
        
    }
                
    @Test
    public void shouldSucceedAccessPublic() throws Exception {  
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/controller/public", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    @Test
    public void shouldRejectWrongClientCredentials() throws Exception {
        ResponseEntity<String> response = getAccessToken("client:123456", "user:secret");
        assertEquals(401, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Authorization is required");
    }
    
    @Test
    public void shouldRejectAccessSecuredWithoutAuth() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/api/controller/secured", String.class);
        assertEquals(401, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Authorization is required");
    }
    
    @Test
    public void shouldRejectWrongUserCredentials() throws Exception {
        ResponseEntity<String> response = getAccessToken("client:", "user:123456");
        assertEquals(401, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Wrong username or password");
    }
    
    @Test
    public void shouldSucceedSecuredWithRightCredentials() throws Exception {
        HttpEntity<?> requestEntity = oAuth2AuthorizedRequest("client:", "user:secret");
        ResponseEntity<String> response = restTemplate.exchange(url + "/api/controller/secured", HttpMethod.GET, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    @Test
    public void shouldRejectPrivateWithWrongUser() throws Exception {  
        HttpEntity<?> requestEntity = oAuth2AuthorizedRequest("client:", "user:secret");
        ResponseEntity<String> response = restTemplate.exchange(url + "/api/controller/private", HttpMethod.GET, requestEntity, String.class);
        assertEquals(403, response.getStatusCodeValue());
        checkJsonPath(response.getBody(), "/message", "Access is denied");
    }
    
    @Test
    public void shouldSucceedPrivateWithRightUser() throws Exception {  
        HttpEntity<?> requestEntity = oAuth2AuthorizedRequest("client:", "admin:secret");
        ResponseEntity<String> response = restTemplate.exchange(url + "/api/controller/private", HttpMethod.GET, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
    
    private HttpEntity<?> oAuth2AuthorizedRequest(String clientCredentials, String userCredentials) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessTokenFromResponse(getAccessToken(clientCredentials, userCredentials)));
        return new HttpEntity<>(headers);
    }
    
    private ResponseEntity<String> getAccessToken(String clientCredentials, String userCredentials) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String encodedCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes(StandardCharsets.UTF_8));
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        String[] usernamePassword = userCredentials.split(":");
        ResponseEntity<String> result = restTemplate.exchange(url + "/oauth/token?grant_type={grant_type}&username={username}&password={password}",
                HttpMethod.POST,
                requestEntity,
                String.class,
                "password",
                usernamePassword[0],
                usernamePassword[1]
        );
        return result;
    }
    
    private String getAccessTokenFromResponse(ResponseEntity<String> response) throws Exception {
        return objectMapper.readTree(response.getBody()).at("/access_token").asText();
    }
            
    private void checkJsonPath(String json, String path, String expectedValue) throws Exception {
        String actualValue = objectMapper.readTree(json).at(path).asText();
        assertEquals(expectedValue, actualValue);
    }
    
}
