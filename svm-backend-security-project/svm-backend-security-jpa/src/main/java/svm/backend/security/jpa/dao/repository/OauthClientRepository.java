package svm.backend.security.jpa.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.security.jpa.dao.entity.OauthClient;

public interface OauthClientRepository extends PagingAndSortingRepository<OauthClient, String> {
    
}
