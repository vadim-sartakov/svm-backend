package svm.backend.signup.controller.confirm;

import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.signup.validator.RegexPatterns;

@Data
public class PhoneConfirmRequest {
    @NotEmpty
    @Pattern(regexp = RegexPatterns.MOBILE_PHONE_PATTERN,
            message = RegexPatterns.WRONG_MOBILE_PHONE_MESSAGE)
    private String phoneNumber;

    @NotEmpty
    private String temporalPassword;

    @NotEmpty
    private String newPassword;
}
