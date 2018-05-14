package svm.backend.web.querydsl;

import com.querydsl.core.types.EntityPath;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface QueryDslDefaultBinder<T extends EntityPath<?>> extends QuerydslBinderCustomizer<T> {

    @Override
    public default void customize(QuerydslBindings bindings, T root) {
        bindings.bind(ZonedDateTime.class).all(new DateTimeBinding<>());
        bindings.bind(Integer.class).all(new NumericBinding<>());
        bindings.bind(Long.class).all(new NumericBinding<>());
        bindings.bind(BigDecimal.class).all(new NumericBinding<>());
        bindings.bind(String.class).all(new StringBinding());
    }

}
