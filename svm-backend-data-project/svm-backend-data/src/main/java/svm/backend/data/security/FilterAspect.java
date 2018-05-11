package svm.backend.data.security;

import com.querydsl.core.types.Predicate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;

@Aspect
public class FilterAspect {
 
    @Around("within(svm.backend.data.security.Filter+) && execution(org.springframework.data.domain.Page org.springframework.data.querydsl.QueryDslPredicateExecutor.findAll(..)) && target(predicateProvider) && args(predicate, pageable)")
    public Object aroundFindAllRepository(ProceedingJoinPoint joinPoint, Filter predicateProvider, Predicate predicate, Pageable pageable) throws Throwable {
        return joinPoint.proceed(new Object[] { predicateProvider.getPredicate().and(predicate), pageable });
    }
    
}
