package svm.backend.security.jpa.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import svm.backend.security.jpa.dao.entity.Oauth2Client;

public interface Oauth2ClientRepository extends PagingAndSortingRepository<Oauth2Client, String> {
    
}
