package svm.backend.dao.repository;

import java.util.UUID;
import svm.backend.dao.entity.User;

public interface UserRepository extends SecuredRepository<User, UUID> {
    
}
