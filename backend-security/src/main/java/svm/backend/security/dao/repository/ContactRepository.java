package svm.backend.security.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.security.dao.entity.useraccount.UserAccount;
import svm.backend.data.repository.GenericRepository;

@RepositoryRestResource(exported = false)
public interface ContactRepository extends GenericRepository<UserAccount, String> {
    
}
