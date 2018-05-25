package svm.backend.core.service;

import java.util.Map;

/**
 * Interface for constructing pojo exceptions.
 * @author Sartakov
 * @param <T>
 */
public interface ExceptionFactory<T> {
    T createException(String code, String message);
    T createDetailedException(String code, String message, Map<String, String> details);
}
