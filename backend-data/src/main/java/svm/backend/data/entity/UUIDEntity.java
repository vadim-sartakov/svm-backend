package svm.backend.data.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import svm.backend.data.entity.listener.OwnerListener;

@Data
@MappedSuperclass
@EntityListeners(OwnerListener.class)
public abstract class UUIDEntity implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "svm.backend.data.generator.UUIDGenerator")
    @Type(type = "uuid-binary")
    @Column(length = 16)
    protected UUID id;
        
    public static <T extends UUIDEntity> T of(UUID id, Class<T> type) {
        
        T newInstance;
        try {
            newInstance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        newInstance.setId(id);
        
        return newInstance;
        
    }
    
    public static <T extends UUIDEntity> T of(String id, Class<T> type) {
        return of(UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)), type);
    }
        
}

