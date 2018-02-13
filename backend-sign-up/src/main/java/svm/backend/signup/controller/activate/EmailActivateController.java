package svm.backend.signup.controller.activate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.signup.controller.EmailAccountController;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}/email")
public class EmailActivateController extends EmailAccountController {
    
    @Value("${svm.backend.signup.controller.sign-up-url}")
    protected String signUpUrl;
        
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmailPassword(@RequestBody @Valid Request passwordRequest, HttpServletRequest request) {
        sendMessage(passwordRequest,
                signUpUrl,
                "svm.backend.signup.controller.EmailPasswordController.activateSubject",
                "svm.backend.signup.controller.EmailPasswordController.activateMessage",
                request);
    }
    
    @PostMapping("confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestParam("id") String id) {
        
    }
    
}
