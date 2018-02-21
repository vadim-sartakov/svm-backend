package svm.backend.signup.controller.restore;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import svm.backend.signup.controller.confirm.PhoneConfirmRequest;
import svm.backend.signup.dao.entity.QTemporalPassword;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.validator.RegexPatterns;
import svm.backend.signup.service.PhonePasswordSender;

public class PhoneRestoreController extends RestoreController {
    
    @Autowired private PhonePasswordSender phonePasswordSender;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @Transactional
    @PostMapping("phone")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendPhonePassword(@RequestBody @Valid RestoreRequest restoreRequest) {
        phonePasswordSender.sendMessage(restoreRequest.phoneNumber);
    }
    
    @PostMapping("phone/confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestBody @Valid ConfirmRequest confirmRequest) {
        TemporalPassword temporalPassword = passwordRepository.findOne(
                QTemporalPassword.temporalPassword.userAccount.account.equalsIgnoreCase(confirmRequest.getPhoneNumber())
        );
        restore(temporalPassword, confirmRequest.newPassword);
    }
    
    @Data
    public static class RestoreRequest {
        @NotEmpty
        @Pattern(regexp = RegexPatterns.MOBILE_PHONE_PATTERN,
                message = RegexPatterns.WRONG_MOBILE_PHONE_MESSAGE)
        private String phoneNumber;
    }
    
    @Getter
    @Setter
    public static class ConfirmRequest extends PhoneConfirmRequest {
        @NotEmpty
        private String newPassword;
    }
    
}
