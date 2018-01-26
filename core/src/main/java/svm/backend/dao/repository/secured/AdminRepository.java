package svm.backend.dao.repository.secured;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.annotation.Secured;
import svm.backend.dao.entity.UserRole;
import svm.backend.dao.repository.CrudRepository;

@NoRepositoryBean
public interface AdminRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    @Secured(UserRole.ADMIN)
    @Override
    public void deleteAll();

    @Secured(UserRole.ADMIN)
    @Override
    public void delete(T entity);

    @Secured(UserRole.ADMIN)
    @Override
    public void delete(ID id);

    @Secured(UserRole.ADMIN)
    @Override
    public <S extends T> S save(S entity);

    @Secured(UserRole.ADMIN)
    @Override
    public T findOne(Predicate predicate);

    @Secured(UserRole.ADMIN)
    @Override
    public T findOne(ID id);

    @Secured(UserRole.ADMIN)
    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable);
    
}
