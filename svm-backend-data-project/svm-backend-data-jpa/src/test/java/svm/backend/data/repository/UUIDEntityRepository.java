package svm.backend.data.repository;

import org.springframework.data.repository.CrudRepository;
import svm.backend.data.entity.UUIDEntity;

public interface UUIDEntityRepository extends CrudRepository<UUIDEntity, Long> {
    
}