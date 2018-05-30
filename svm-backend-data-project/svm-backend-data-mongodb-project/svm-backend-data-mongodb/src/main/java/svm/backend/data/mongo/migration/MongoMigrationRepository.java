package svm.backend.data.mongo.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import svm.backend.data.migration.model.Migration;
import svm.backend.data.migration.service.MigrationRepository;

public class MongoMigrationRepository implements MigrationRepository {
    
    @Autowired private MongoTemplate template;

    @Override
    public Migration save(Migration migration) {
        template.save(
                MongoMigration.builder()
                        .id(migration.getId())
                        .build()
        );
        return migration;
    }

    @Override
    public Migration findOne(String id) {
        return template.findById(id, MongoMigration.class);
    }

    @Override
    public void delete(String id) {
        template.remove(template.findById(id, MongoMigration.class));
    }

    @Override
    public void delete(Migration migration) {
        template.remove(migration);
    }
    
}
