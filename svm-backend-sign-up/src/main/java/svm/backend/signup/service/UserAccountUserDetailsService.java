package svm.backend.signup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.security.service.UsernameUserDetailsService;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.UserAccountRepository;

public class UserAccountUserDetailsService extends UsernameUserDetailsService {

    @Autowired private UserAccountRepository userAccountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        try {
            UserDetails details = super.loadUserByUsername(username);
            return details;
        } catch (UsernameNotFoundException e) {
            
            UserAccount userAccount = userAccountRepository.findByAccountIgnoreCase(username);

            if (userAccount == null)
                throw new UsernameNotFoundException(username);
        
            return userAccount.getUser();
            
        }
                
    }
    
}
