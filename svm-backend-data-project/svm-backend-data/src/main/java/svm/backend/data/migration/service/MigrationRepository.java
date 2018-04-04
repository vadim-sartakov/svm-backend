package svm.backend.data.migration.service;

import svm.backend.data.migration.model.Migration;

public interface MigrationRepository {
    Migration save(Migration migration);
    Migration findById(String id);
    Migration delete(String id);
    Migration delete(Migration migration);
}
