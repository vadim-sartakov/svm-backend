package svm.backend.security.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import svm.backend.security.dao.entity.useraccount.UserAccount;
import svm.backend.data.repository.GenericRepository;

@RepositoryRestResource(exported = false)
public interface UserAccountRepository extends GenericRepository<UserAccount, String> {
    
    @RestResource(exported = false)
    public UserAccount findByAccountIgnoreCase(String account);
    
}
