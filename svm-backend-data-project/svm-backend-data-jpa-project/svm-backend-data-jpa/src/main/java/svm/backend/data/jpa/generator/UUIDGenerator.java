package svm.backend.data.jpa.generator;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import svm.backend.data.jpa.entity.Identifiable;

public class UUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor si, Object object) throws HibernateException {
        if (object instanceof Identifiable) {
            Identifiable entity = (Identifiable) object;
            Serializable providedId = entity.getId();
            return providedId == null ? UUID.randomUUID() : providedId;
        } else
            throw new RuntimeException("UUID generator can be applied only on Identifiable entities!");
    }
    
}
