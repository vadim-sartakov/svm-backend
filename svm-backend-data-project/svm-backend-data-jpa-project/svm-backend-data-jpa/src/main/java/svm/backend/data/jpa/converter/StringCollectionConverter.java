package svm.backend.data.jpa.converter;

import java.util.Collection;
import javax.persistence.AttributeConverter;
import liquibase.util.StringUtils;

public interface StringCollectionConverter<T extends Collection<String>> extends AttributeConverter<T, String> {

    @Override
    default String convertToDatabaseColumn(T attribute) {
        return attribute == null ? null :
                StringUtils.join(attribute.toArray(new String[attribute.size()]), ",");
    }
    
}
