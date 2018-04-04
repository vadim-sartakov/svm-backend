package svm.backend.data.migration.model;

public interface MigrationRollback extends Migration {
    /**
     * It's better to set this property in external application.properties
     * @return 
     */
    boolean shouldRollback();
    void rollback();    
}
