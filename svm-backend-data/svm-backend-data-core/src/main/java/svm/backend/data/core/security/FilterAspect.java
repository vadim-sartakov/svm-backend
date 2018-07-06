package svm.backend.data.core.security;

import com.querydsl.core.types.Predicate;
import java.io.Serializable;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class FilterAspect {
 
    @Around("within(svm.backend.data.core.security.Filter+) && "
            + "execution(org.springframework.data.domain.Page org.springframework.data.querydsl.QuerydslPredicateExecutor.findAll(..)) && "
            + "target(predicateProvider) && "
            + "args(predicate, pageable)")
    public Object aroundFindAllRepository(ProceedingJoinPoint joinPoint, Filter predicateProvider, Predicate predicate, Pageable pageable) throws Throwable {
        return joinPoint.proceed(new Object[] { predicateProvider.getFindAllPredicate().and(predicate), pageable });
    }
    
    @SuppressWarnings("unchecked")
    @Around("within(svm.backend.data.core.security.Filter+) && "
            + "execution(* org.springframework.data.repository.CrudRepository.findById(..)) && "
            + "target(predicateProvider) && "
            + "args(id)")
    public <T, ID extends Serializable> Optional<T> aroundFindOneRepository(ProceedingJoinPoint joinPoint, ID id, Filter predicateProvider) {
        QuerydslPredicateExecutor<T> repository = (QuerydslPredicateExecutor<T>) predicateProvider;
        return repository.findOne(predicateProvider.getFindOnePredicate(id));
    }
    
}
