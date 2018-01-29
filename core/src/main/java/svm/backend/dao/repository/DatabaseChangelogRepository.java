package svm.backend.dao.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import svm.backend.dao.entity.DatabaseChangelog;

@RepositoryRestResource(exported = false)
public interface DatabaseChangelogRepository extends GenericRepository<DatabaseChangelog, String>{
    
}
