/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.core.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.querydsl.binding.MultiValueBinding;

public class DateTimeBinding<S extends Temporal & Comparable<?>> implements MultiValueBinding<DateTimePath<S>, S> {

    @Override
    public Optional<Predicate> bind(DateTimePath<S> path, Collection<? extends S> values) {

        Iterator<? extends S> iterator = values.iterator();
        S valueOne, valueTwo;
        valueOne = iterator.next();

        if (!iterator.hasNext()) {
            return Optional.of(path.eq(valueOne));
        }

        valueTwo = iterator.next();
        if (iterator.hasNext()) {
            return Optional.of(path.in(values));
        }

        return Optional.of(path.between(valueOne, valueTwo));

    }

}
