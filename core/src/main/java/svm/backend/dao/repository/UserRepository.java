package svm.backend.dao.repository;

import java.util.UUID;
import svm.backend.dao.entity.User;
import svm.backend.dao.repository.secured.AdminRepository;

public interface UserRepository extends PrivilegedRepository<User, UUID>,
        AdminRepository<User, UUID> {
    
}
