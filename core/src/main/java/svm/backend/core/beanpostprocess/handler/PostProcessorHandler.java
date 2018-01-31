package svm.backend.core.beanpostprocess.handler;

public interface PostProcessorHandler<T> {
    String getQualifier();
    T postProcessBeforeInitialization(T bean, String beanName);
    T postProcessAfterInitialization(T bean, String beanName);
}
