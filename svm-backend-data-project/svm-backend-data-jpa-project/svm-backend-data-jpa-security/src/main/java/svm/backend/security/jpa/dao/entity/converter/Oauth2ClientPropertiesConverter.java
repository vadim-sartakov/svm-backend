package svm.backend.security.jpa.dao.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import svm.backend.security.jpa.dao.entity.Oauth2Client;

public class Oauth2ClientPropertiesConverter implements AttributeConverter<Oauth2Client.Properties, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Oauth2Client.Properties attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Oauth2Client.Properties convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Oauth2Client.Properties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
