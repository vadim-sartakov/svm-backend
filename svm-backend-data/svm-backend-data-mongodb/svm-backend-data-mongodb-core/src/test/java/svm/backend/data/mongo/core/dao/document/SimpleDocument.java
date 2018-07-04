package svm.backend.data.mongo.core.dao.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class SimpleDocument {
    @Id
    private String id;
    private String number;
}
