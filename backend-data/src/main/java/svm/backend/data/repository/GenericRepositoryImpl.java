package svm.backend.data.repository;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

public class GenericRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
        implements GenericRepository<T, ID> {
    
    public GenericRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Page<T> superFindAll(Predicate predicate, Pageable pageable) {
        return super.findAll(predicate, pageable);
    }

    @Override
    public T superFindOne(ID id) {
        return super.findOne(id);
    }

    @Override
    public T superSave(T entity) {
        return super.save(entity);
    }

    @Override
    public void superDelete(ID id) {
        super.delete(id);
    }
    
}
