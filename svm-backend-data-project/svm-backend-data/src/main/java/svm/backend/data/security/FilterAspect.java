package svm.backend.data.security;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class FilterAspect {
 
    @Around("within(svm.backend.data.security.Filter+) && "
            + "execution(org.springframework.data.domain.Page org.springframework.data.querydsl.QueryDslPredicateExecutor.findAll(..)) && "
            + "target(predicateProvider) && "
            + "args(predicate, pageable)")
    public Object aroundFindAllRepository(ProceedingJoinPoint joinPoint, Filter predicateProvider, Predicate predicate, Pageable pageable) throws Throwable {
        return joinPoint.proceed(new Object[] { predicateProvider.getFindAllPredicate().and(predicate), pageable });
    }
    
    @SuppressWarnings("unchecked")
    @Around("within(svm.backend.data.security.Filter+) && "
            + "execution(* org.springframework.data.repository.CrudRepository.findOne(..)) && "
            + "target(predicateProvider) && "
            + "args(id)")
    public <T, ID extends Serializable> T aroundFindOneRepository(ProceedingJoinPoint joinPoint, ID id, Filter predicateProvider) {
        QueryDslPredicateExecutor<T> repository = (QueryDslPredicateExecutor<T>) predicateProvider;        
        return repository.findOne(predicateProvider.getFindOnePredicate(id));
    }
    
}
