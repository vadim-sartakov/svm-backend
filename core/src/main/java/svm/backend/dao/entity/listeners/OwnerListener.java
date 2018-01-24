package svm.backend.dao.entity.listeners;

import java.util.Collection;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ReflectionUtils;

/**
 * Automatically sets owners in one-to-many relations.
 * @author Sartakov
 */
public class OwnerListener {
    
    @SuppressWarnings("unchecked")
    @PrePersist
    public void setOwner(Object entity) {
        
        ReflectionUtils.doWithFields(entity.getClass(), field -> {

            String ownerField = field.getAnnotation(OneToMany.class).mappedBy();
            
            Collection collection = PropertyAccessorFactory
                    .forBeanPropertyAccess(entity)
                    .convertIfNecessary(field.getName(), Collection.class);
            
            collection.forEach(item -> {
                
                PropertyAccessorFactory
                        .forBeanPropertyAccess(item)
                        .setPropertyValue(ownerField, entity);
                
            });
            
        }, field -> field.getType().isAssignableFrom(Collection.class) &&
                field.getAnnotation(OneToMany.class) != null);
        
    }
    
}
