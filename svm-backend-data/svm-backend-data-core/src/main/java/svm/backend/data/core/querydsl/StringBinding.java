package svm.backend.data.core.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.querydsl.binding.MultiValueBinding;

public class StringBinding implements MultiValueBinding<StringPath, String> {
    @Override
    public Optional<Predicate> bind(StringPath path, Collection<? extends String> values) {
        Iterator<? extends String> iterator = values.iterator();
        String value = iterator.next();
        return Optional.of(iterator.hasNext() ? path.in(values) : path.containsIgnoreCase(value));
    }
}
