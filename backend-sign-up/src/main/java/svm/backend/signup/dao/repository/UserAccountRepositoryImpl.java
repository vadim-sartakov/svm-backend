package svm.backend.signup.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import svm.backend.signup.dao.entity.user.account.Email;
import svm.backend.signup.dao.entity.user.account.UserAccount;

public class UserAccountRepositoryImpl {
    
    @Autowired private UserAccountRepository repository;
    @Autowired private MessageSource messageSource;
    @Autowired private JavaMailSender mailSender;
    
    public UserAccount save(UserAccount account) {
        
        if (!(account instanceof Email))
            return repository.superSave(account);
        
        Email emailAccount = (Email) account;
        
        String message = messageSource.getMessage(
                "",
                new Object[] {  },
                LocaleContextHolder.getLocale());
        
        
        return repository.superSave(emailAccount);

    }
    
}
