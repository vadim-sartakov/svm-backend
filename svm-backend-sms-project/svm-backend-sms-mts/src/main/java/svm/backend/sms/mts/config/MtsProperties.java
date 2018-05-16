package svm.backend.sms.mts.config;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("svm.backend.sms.mts")
public class MtsProperties implements InitializingBean {
    
    private String url;
    private String sendUrl;
    private String getStatusUrl;
    private String login;
    private String password;
    private String encodedPassword;
    /** Subscription */
    private String naming;

    @Override
    public void afterPropertiesSet() throws Exception {
        encodedPassword = DigestUtils.md5Hex(password);
    }
}
