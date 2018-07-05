package svm.backend.data.jpa.security.dao.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import svm.backend.data.jpa.security.dao.entity.Role;
import svm.backend.data.jpa.security.dao.entity.User;

@Secured({ Role.ROLE_SYSTEM, Role.ROLE_MODERATOR, Role.ROLE_ADMIN })
public interface UserRepository extends PagingAndSortingRepository<User, UUID>, 
        QueryDslPredicateExecutor<User> {
    @RestResource(exported = false)
    @EntityGraph("UserOverview")
    User findByUsername(String username);
}
