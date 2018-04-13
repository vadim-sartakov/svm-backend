package svm.backend.signup.controller.confirm;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.signup.validator.PhoneNumber;

@Data
public class PhoneConfirmRequest {
    @NotEmpty
    @PhoneNumber
    private String phoneNumber;

    @NotEmpty
    private String temporalPassword;

    @NotEmpty
    private String newPassword;
}
