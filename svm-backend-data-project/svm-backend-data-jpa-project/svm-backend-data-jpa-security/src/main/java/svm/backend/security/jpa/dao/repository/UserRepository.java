package svm.backend.security.jpa.dao.repository;

import java.util.UUID;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import svm.backend.security.jpa.dao.entity.Role;
import svm.backend.security.jpa.dao.entity.User;

@Secured({ Role.SYSTEM, Role.MODERATOR, Role.ADMIN })
public interface UserRepository extends PagingAndSortingRepository<User, UUID>,
        QueryDslPredicateExecutor<User> {
    
}
