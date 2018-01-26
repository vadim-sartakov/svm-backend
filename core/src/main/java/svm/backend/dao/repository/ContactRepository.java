package svm.backend.dao.repository;

import java.util.UUID;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.dao.entity.User;
import svm.backend.dao.repository.secured.AdminRepository;

@RepositoryRestResource(exported = false)
public interface ContactRepository extends PrivilegedRepository<User, UUID>,
        AdminRepository<User, UUID> {
    
}
