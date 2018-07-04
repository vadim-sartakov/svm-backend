package svm.backend.data.core.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.data.querydsl.binding.MultiValueBinding;

public class NumericBinding<S extends Number & Comparable<?>> implements MultiValueBinding<NumberPath<S>, S> {

    @Override
    public Predicate bind(NumberPath<S> path, Collection<? extends S> values) {
        
        Iterator<? extends S> iterator = values.iterator();
        S valueOne, valueTwo;
        valueOne = iterator.next();

        if (!iterator.hasNext()) {
            return path.eq(valueOne);
        }

        valueTwo = iterator.next();
        if (iterator.hasNext()) {
            return path.in(values);
        }

        return path.between(valueOne, valueTwo);
        
    }
    
}
