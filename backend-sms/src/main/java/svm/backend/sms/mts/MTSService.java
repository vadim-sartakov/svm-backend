package svm.backend.sms.mts;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import svm.backend.sms.SMSSender;

public class MTSService implements SMSSender {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(MTSService.class);
    
    private static final String URL = "https://api.mcommunicator.ru/m2m/m2m_api.asmx";
    private static final String SEND_MESSAGE_PATH = "SendMessage";
    private static final String GET_MESSAGE_STATUS_PATH = "GetMessageStatus";
    
    @Value("${svm.backend.sms.mts.naming}")
    private String naming;
    
    @Value("${svm.backend.sms.mts.login}")
    private String login;
    
    @Value("${svm.backend.sms.mts.password}")
    private String password;
    private String encodedPassword;
    
    @PostConstruct
    public void initialize() {
        encodedPassword = DigestUtils.md5Hex(password);
    }
        
    @Override
    public Object sendMessage(String phoneNumber, String message) {
        
        phoneNumber = phoneNumber
                .replaceAll("\\+(\\d)", "7")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
        
        Client client = ClientBuilder.newClient();
        WebTarget rootTarget = client.target(URL);
        WebTarget target = rootTarget.path(SEND_MESSAGE_PATH);
        
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("msid", phoneNumber);
        form.add("message", message);
        form.add("naming", naming);
        form.add("login", login);
        form.add("password", encodedPassword);
        
        Response response = target.request()
                .post(Entity.form(form));
        
        int status = response.getStatus();        
        if (status != HttpServletResponse.SC_OK) {
            LOGGER.error("Failed to send message to {}. Server responded with code {}.{}",
                    phoneNumber,
                    status,
                    response.readEntity(String.class)
            );
            throw new RuntimeException("Не удалось отправить сообщение");
        }
        
        long messageId = response.readEntity(SendMessageResponse.class).id;
        LOGGER.debug("SMS to {} has been successfully sent with id {}", phoneNumber, messageId);
        
        return messageId;
        
    }
        
    @Override
    public ArrayOfDeliveryInfo getMessageStatus(Object id) {
        
        Client client = ClientBuilder.newClient();
        WebTarget rootTarget = client.target(URL);
        WebTarget target = rootTarget.path(GET_MESSAGE_STATUS_PATH)
                .queryParam("messageId", id)
                .queryParam("login", login)
                .queryParam("password", encodedPassword);
        
        Response response = target.request().get();
        ArrayOfDeliveryInfo statusList = response.readEntity(ArrayOfDeliveryInfo.class);

        int status = response.getStatus();
        if (status != HttpServletResponse.SC_OK) {
            LOGGER.error("Failed to get message status for {}", id);
            throw new RuntimeException("Failed to get message status");
        }

        return statusList;
        
    }
                    
}
