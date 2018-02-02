package svm.backend.dao.datachange;

public interface DataChangeRollback extends DataChange {
    boolean shouldRollback();
    void rollback();
}
