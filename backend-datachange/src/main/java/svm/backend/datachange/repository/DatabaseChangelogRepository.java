package svm.backend.datachange.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.data.repository.GenericRepository;
import svm.backend.datachange.entity.DatabaseChangelog;

@RepositoryRestResource(exported = false)
public interface DatabaseChangelogRepository extends GenericRepository<DatabaseChangelog, String>{
    
}
