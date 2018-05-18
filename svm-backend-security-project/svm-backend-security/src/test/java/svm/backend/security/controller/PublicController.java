package svm.backend.security.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    
    @GetMapping
    public Response get() {
        return new Response();
    }
    
            
   @Data
   public static class Response {
       private String content = "test";
   }

}
