package svm.backend.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.dao.entity.useraccount.UserAccount;

@RepositoryRestResource(exported = false)
public interface ContactRepository extends GenericRepository<UserAccount, String> {
    
}
