package svm.backend.security.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import svm.backend.security.jpa.dao.repository.Oauth2ClientRepository;

public class OauthClientDetailsService implements ClientDetailsService {

    @Autowired private Oauth2ClientRepository repository;
    
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return repository.findOne(clientId);
    }
    
}
