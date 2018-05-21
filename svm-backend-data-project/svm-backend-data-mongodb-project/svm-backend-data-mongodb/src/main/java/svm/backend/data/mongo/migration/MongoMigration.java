package svm.backend.data.mongo.migration;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import svm.backend.data.migration.model.Migration;

@Data
@Builder
@Document
public class MongoMigration implements Migration {
    @Id
    private String id;
    private Integer executionOrder;
}
