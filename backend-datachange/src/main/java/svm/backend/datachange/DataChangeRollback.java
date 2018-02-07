package svm.backend.datachange;

public interface DataChangeRollback extends DataChange {
    boolean shouldRollback();
    void rollback();
}
