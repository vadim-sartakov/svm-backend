package svm.backend.data.jpa.converter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class StringSetConverter implements StringCollectionConverter<Set<String>> {

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new LinkedHashSet<>(Arrays.asList(dbData.split(",")));
    }
    
}
