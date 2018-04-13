package svm.backend.signup.controller.confirm;

import javax.validation.Valid;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import svm.backend.signup.dao.entity.QTemporalPassword;
import svm.backend.signup.dao.entity.TemporalPassword;

public class EmailConfirmController extends ConfirmController {
    
    @PostMapping("email")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestBody @Valid ConfirmRequest confirmRequest) {
        TemporalPassword temporalPassword = passwordRepository.findOne(
                QTemporalPassword.temporalPassword.password.eq(confirmRequest.password)
        );
        super.confirm(temporalPassword);
    }
    
    @Data
    public static class ConfirmRequest {
        @NotEmpty
        private String password;
    }
    
}
