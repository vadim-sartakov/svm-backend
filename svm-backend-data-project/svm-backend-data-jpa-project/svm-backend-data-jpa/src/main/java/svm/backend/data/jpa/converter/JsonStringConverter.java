package svm.backend.data.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;

public interface JsonStringConverter extends AttributeConverter<Object, String> {
    
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    default String convertToDatabaseColumn(Object attribute) {
        try {
            return attribute == null ? null : OBJECT_MAPPER.writeValueAsString(attribute);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Object convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : OBJECT_MAPPER.readValue(dbData, getClass());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    } 
            
}
