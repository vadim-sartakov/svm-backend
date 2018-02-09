package svm.backend.signup.dao.repository;

import org.springframework.data.rest.core.annotation.RestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.signup.dao.entity.user.account.UserAccount;

public interface UserAccountRepository extends GenericRepository<UserAccount, String> {
    
    @RestResource(exported = false)
    public UserAccount findByAccountIgnoreCase(String account);
    
}
