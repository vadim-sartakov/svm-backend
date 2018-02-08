package svm.backend.signup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${svm.backend.signup.controller.temporal-password-url:password}/email")
public class EmailPasswordController {
    
}
