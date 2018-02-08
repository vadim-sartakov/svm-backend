package svm.backend.signup.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.signup.dao.entity.TemporalPassword;

@RepositoryRestResource(exported = false)
public interface TemporalPasswordRepository extends GenericRepository<TemporalPassword, String> {
    TemporalPassword findByAccountIgnoreCase(String account);
}
