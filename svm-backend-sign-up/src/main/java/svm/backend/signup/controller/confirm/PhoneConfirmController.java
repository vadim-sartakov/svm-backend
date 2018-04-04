package svm.backend.signup.controller.confirm;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import svm.backend.signup.dao.entity.QTemporalPassword;
import svm.backend.signup.dao.entity.TemporalPassword;

public class PhoneConfirmController extends ConfirmController {
        
    @PostMapping("phone")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestBody @Valid PhoneConfirmRequest confirmRequest) {
        TemporalPassword temporalPassword = passwordRepository.findOne(
                QTemporalPassword.temporalPassword.userAccount.account.equalsIgnoreCase(confirmRequest.getPhoneNumber())
                        .and(QTemporalPassword.temporalPassword.password.eq(confirmRequest.getTemporalPassword()))
        );
        super.confirm(temporalPassword);
    }
        
}
