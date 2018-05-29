package svm.backend.samples.jpa.account.config.migration;

import java.util.Arrays;
import java.util.LinkedHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.jpa.security.dao.entity.JpaGrantedAuthority;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.Role;
import svm.backend.data.jpa.security.dao.entity.User;
import svm.backend.data.jpa.security.dao.repository.Oauth2ClientRepository;
import svm.backend.data.jpa.security.dao.repository.UserRepository;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.security.util.SecurityUtils;

@Component
@Order(0)
public class Account_1_0_0 implements MigrationUpdate {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private Oauth2ClientRepository clientRepository;

    @Transactional
    @Override
    public void update() {
        
        SecurityUtils.setAuthentication(User.ADMIN);
        
        User user = new User();
        user.setUsername("manager");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setAuthorities(Arrays.asList(new JpaGrantedAuthority(Role.ADMIN)));
        userRepository.save(user);
        
        Oauth2Client client = new Oauth2Client();
        client.setClientId("web");
        client.setClientSecret(passwordEncoder.encode("123456"));
        client.setAuthorizedGrantTypes(new LinkedHashSet<>(Arrays.asList("client_credentials", "password", "refresh_token")));
        client.setScope(new LinkedHashSet<>(Arrays.asList("read", "write")));
        client.setAuthorities(Arrays.asList(new JpaGrantedAuthority(Role.ADMIN)));
        clientRepository.save(client);
        
        SecurityUtils.restoreAuthentication();
        
    }

    @Override
    public String getId() {
        return "shop-1.0.0";
    }
    
}
