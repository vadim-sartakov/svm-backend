package svm.backend.web.controller;

import javax.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping
    public String get() {
        return "response";
    }
        
    @PostMapping
    public void post(@RequestBody @Valid Request request) { }
    
    @GetMapping("/error")
    public String getError() {
        throw new RuntimeException("test");
    }
    
    @Data
    public static class Request {
        private String content;
    }
    
}
