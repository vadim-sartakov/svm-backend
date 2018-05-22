package svm.backend.samples.shop.config.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.User;
import svm.backend.data.jpa.security.dao.repository.Oauth2ClientRepository;
import svm.backend.data.jpa.security.dao.repository.UserRepository;

public class Shop_1_0_0 implements MigrationUpdate {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private Oauth2ClientRepository clientRepository;
    @Autowired private UserRepository userRepository;
    
    @Transactional
    @Override
    public void update() {
        
        User user = User.ADMIN;
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
        
        Oauth2Client client = clientRepository.findOne(Oauth2Client.DEFAULT.getId());
        String encodedPassword = passwordEncoder.encode("123456");
        client.setClientSecret(encodedPassword);
        clientRepository.save(client);
        
        user = userRepository.findOne(user.getId());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        
    }

    @Override
    public String getId() {
        return "shop-1.0.0";
    }

    @Override
    public Integer getExecutionOrder() {
        return 1;
    }
    
}
