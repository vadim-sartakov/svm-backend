package svm.backend.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void post() { }
    
    @GetMapping("/error")
    public String getError() {
        throw new RuntimeException("test");
    }
    
}
