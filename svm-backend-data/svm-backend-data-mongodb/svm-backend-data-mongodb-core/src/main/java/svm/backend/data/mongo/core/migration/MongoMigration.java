package svm.backend.data.mongo.core.migration;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import svm.backend.data.core.migration.model.Migration;

@Data
@Builder
@Document
public class MongoMigration implements Migration {
    @Id
    private String id;
    private Integer executionOrder;
}
