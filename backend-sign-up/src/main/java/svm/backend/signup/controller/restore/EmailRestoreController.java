package svm.backend.signup.controller.restore;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import svm.backend.signup.dao.entity.QTemporalPassword;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.validator.RegexPatterns;
import svm.backend.signup.service.EmailPasswordSender;

public class EmailRestoreController extends RestoreController {
    
    @Value("${svm.backend.signup.controller.restore-url}")
    protected String restoreUrl;
    
    @Autowired private EmailPasswordSender emailPasswordSender;
    
    @Transactional
    @PostMapping("email")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendPasswordEmail(@RequestBody @Valid RestoreRequest restoreRequest, HttpServletRequest request) {
        emailPasswordSender.sendMessage(restoreRequest.email,
                restoreUrl,
                "svm.backend.signup.controller.EmailPasswordController.restoreSubject",
                "svm.backend.signup.controller.EmailPasswordController.restoreMessage",
                request);
    }
    
    @PostMapping("email/confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm(@RequestBody @Valid ConfirmRequest confirmRequest) {
        TemporalPassword temporalPassword = passwordRepository.findOne(
                QTemporalPassword.temporalPassword.password.eq(confirmRequest.temporalPassword)
        );
        if (temporalPassword == null) {
            logger.warn(
                    "Attempt to restore password with wrong temporal password {}",
                    confirmRequest.temporalPassword
            );
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) { }
            return;
        }
        restore(temporalPassword, confirmRequest.newPassword);
    }
    
    @Data
    public static class RestoreRequest {
        @NotEmpty
        @Pattern(regexp = RegexPatterns.EMAIL_PATTERN,
                message = RegexPatterns.WRONG_EMAIL_MESSAGE)
        private String email;
    }
    
    @Data
    public static class ConfirmRequest {
        
        @NotEmpty
        private String temporalPassword;
        
        @NotEmpty
        private String newPassword;
        
    }
        
}
