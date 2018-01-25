package svm.backend.dao.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import svm.backend.dao.entity.listeners.OwnerListener;

@Data
@Entity
@EntityListeners(OwnerListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UUIDEntity implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "svm.backend.dao.generator.UUIDGenerator")
    @Type(type = "uuid-binary")
    @Column(length = 16)
    protected UUID id;
        
    public static <T extends UUIDEntity> T newInstance(UUID id, Class<T> type) {
        
        T newInstance;
        try {
            newInstance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
}

        newInstance.setId(id);
        
        return newInstance;
        
    }
    
}

