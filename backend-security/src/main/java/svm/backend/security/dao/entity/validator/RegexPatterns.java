package svm.backend.security.dao.entity.validator;

public class RegexPatterns {
    
    public static final String MOBILE_PHONE_PATTERN = "^\\+\\d{1,2}\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}$";
    public static final String WRONG_MOBILE_PHONE_MESSAGE = "{svm.backend.dao.entity.useraccount.PhoneNumber.message}";
    
    public static final String EMAIL_PATTERN = "^([A-Za-z0-9_\\.-]+\\@[\\dA-Za-z\\.-]+\\.[a-z\\.]{2,6})$";
    public static final String WRONG_EMAIL_MESSAGE = "{svm.backend.dao.entity.useraccount.Email.message}";
    
}
