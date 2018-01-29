package svm.backend.dao.migration;

public interface Migration {
    String getId();  
    void execute();
}
