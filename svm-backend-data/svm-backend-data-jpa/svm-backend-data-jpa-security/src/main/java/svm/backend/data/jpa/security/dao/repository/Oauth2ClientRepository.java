package svm.backend.data.jpa.security.dao.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.Role;

@Secured({ Role.ROLE_SYSTEM, Role.ROLE_MODERATOR, Role.ROLE_ADMIN })
public interface Oauth2ClientRepository extends PagingAndSortingRepository<Oauth2Client, UUID> {
    @RestResource(exported = false)
    @EntityGraph("Oauth2ClientOverview")
    Oauth2Client findByClientId(String id);
}
