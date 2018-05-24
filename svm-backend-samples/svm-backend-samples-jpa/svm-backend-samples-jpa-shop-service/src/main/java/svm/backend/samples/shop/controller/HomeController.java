package svm.backend.samples.shop.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping("/public")
    public Response getPublic() {
        return Response.builder().content("Response from public").build();
    }
    
    @GetMapping("/protected")
    public Response getProtected() {
        return Response.builder().content("Response from protected").build();
    }
    
    @Data
    @Builder
    public static class Response {
        private String content;
    }
            
}
