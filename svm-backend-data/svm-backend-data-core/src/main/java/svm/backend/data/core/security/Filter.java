package svm.backend.data.core.security;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.io.Serializable;

/**
 * This interface is applied on repositories for security purposes.
 * @author Sartakov
 * @param <ID>
 */
public interface Filter<ID extends Serializable> {
    BooleanExpression getFindOnePredicate(ID id);
    BooleanExpression getFindAllPredicate();
}
