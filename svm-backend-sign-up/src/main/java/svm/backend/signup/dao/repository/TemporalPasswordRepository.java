package svm.backend.signup.dao.repository;

import java.util.UUID;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.user.account.UserAccount;

@RepositoryRestResource(exported = false)
public interface TemporalPasswordRepository extends GenericRepository<TemporalPassword, UUID> {
    TemporalPassword findByUserAccount(UserAccount userAccount);
}
