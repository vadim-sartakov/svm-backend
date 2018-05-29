package svm.backend.security.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/controller")
public class TestController {
    
    @GetMapping("/public")
    public Response getPublic() {
        return new Response("Public");
    }
    
    @GetMapping("/secured")
    public Response getSecured() {
        return new Response("Secured");
    }
    
    @GetMapping("/private")
    public Response getPrivate() {
        return new Response("Private");
    }
    
   @Data
   public static class Response {
       private final String content;
   }

}
