package svm.backend.signup.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.signup.dao.entity.EmailPassword;

@RepositoryRestResource(exported = false)
public interface EmailPasswordRepository extends GenericRepository<EmailPassword, String> {
    EmailPassword findByAccountIgnoreCase(String account);
}
