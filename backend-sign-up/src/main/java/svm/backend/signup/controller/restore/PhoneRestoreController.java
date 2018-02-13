package svm.backend.signup.controller.restore;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.signup.controller.PhoneAccountController;

@RestController
@RequestMapping("${svm.backend.signup.controller.restore-url}/phone}")
public class PhoneRestoreController extends PhoneAccountController {
    
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendPhonePassword(@RequestBody @Valid Request passwordRequest) {
        sendMessage(passwordRequest);
    }
    
    @PostMapping("confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestParam("id") String id) {
        
    }
    
}
