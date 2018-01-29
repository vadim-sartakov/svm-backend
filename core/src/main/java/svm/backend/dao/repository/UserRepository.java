package svm.backend.dao.repository;

import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import svm.backend.dao.entity.User;
import svm.backend.dao.entity.UserRole;

public interface UserRepository extends GenericRepository<User, UUID> {

    @Secured({ UserRole.ADMIN, UserRole.MODERATOR })
    @Override
    public Page<User> findAll(Predicate predicate, Pageable pageable);

    @Override
    public User findOne(UUID id);

    @Override
    public <S extends User> S save(S entity);
    
    @Secured({ UserRole.ADMIN, UserRole.MODERATOR })
    @Override
    public void delete(UUID id);
    
    @EntityGraph("user.overview")
    @RestResource(exported = false)
    public User findByUsername(String username);
    
}
