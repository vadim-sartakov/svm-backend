package svm.backend.samples.shop.config.migration;

import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.jpa.security.dao.entity.JpaGrantedAuthority;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.User;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.samples.shop.dao.entity.Role;
import svm.backend.security.model.BaseOauth2Client.Properties;
import svm.backend.security.util.SecurityUtils;

public class Shop_1_0_0 implements MigrationUpdate {

    @PersistenceContext private EntityManager entityManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void update() {
        
        SecurityUtils.setAuthentication(User.ADMIN);
        
        User user = new User();
        user.setUsername("manager");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setAuthorities(Arrays.asList(new JpaGrantedAuthority(Role.ADMIN)));
        entityManager.persist(entityManager.merge(user));
        
        Oauth2Client client = new Oauth2Client();
        client.setClientId("web");
        client.setClientSecret(passwordEncoder.encode("123456"));
        client.setProperties(Properties.builder()
                .authorizedGrantType("client_credentials")
                .authorizedGrantType("password")
                .authorizedGrantType("refresh_token")
                .build());
        client.setAuthorities(Arrays.asList(new JpaGrantedAuthority(Role.ADMIN)));
        entityManager.persist(entityManager.merge(client));
        
        SecurityUtils.restoreAuthentication();
        
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
