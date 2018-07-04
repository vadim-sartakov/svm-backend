package svm.backend.data.jpa.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.User;
import svm.backend.data.jpa.security.dao.repository.Oauth2ClientRepository;
import svm.backend.security.util.SecurityUtils;

public class OauthClientDetailsService implements ClientDetailsService {

    @Autowired private Oauth2ClientRepository repository;
    
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        SecurityUtils.setAuthentication(User.SYSTEM);
        Oauth2Client client = repository.findByClientId(clientId);
        SecurityUtils.restoreAuthentication();
        return client;
    }
    
}
