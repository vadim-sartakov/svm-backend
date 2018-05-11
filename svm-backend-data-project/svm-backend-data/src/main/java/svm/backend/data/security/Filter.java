package svm.backend.data.security;

import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * This interface is applied on repositories for security purposes.
 * @author Sartakov
 */
public interface Filter {
    BooleanExpression getPredicate();
}
