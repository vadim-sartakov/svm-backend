package svm.backend.data.core.migration.service;

import svm.backend.data.core.migration.model.Migration;

public interface MigrationRepository {
    Migration save(Migration migration);
    Migration findOne(String id);
    void delete(String id);
    void delete(Migration migration);
}
