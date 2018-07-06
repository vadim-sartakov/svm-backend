package svm.backend.data.jpa.core.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import svm.backend.data.jpa.core.entity.Identifiable;

import java.io.Serializable;
import java.util.UUID;

public class UUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor si, Object object) throws HibernateException {
        if (object instanceof Identifiable) {
            Identifiable entity = (Identifiable) object;
            Serializable providedId = entity.getId();
            return providedId == null ? UUID.randomUUID() : providedId;
        } else
            throw new RuntimeException("UUID generator can be applied only on Identifiable entities!");
    }
    
}

