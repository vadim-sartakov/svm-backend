package svm.backend.signup.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.signup.dao.entity.EmailPassword;
import svm.backend.signup.dao.entity.PhonePassword;

@RepositoryRestResource(exported = false)
public interface PhonePasswordRepository extends GenericRepository<PhonePassword, String> {
    EmailPassword findByAccountIgnoreCase(String account);
}
