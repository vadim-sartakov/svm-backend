package svm.backend.data.jpa.entity.listener;

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
            
            Collection collection = (Collection) PropertyAccessorFactory
                    .forBeanPropertyAccess(entity)
                    .getPropertyValue(field.getName());
            
            if (collection == null)
                return;
            
            collection.forEach(item -> {
                
                PropertyAccessorFactory
                        .forBeanPropertyAccess(item)
                        .setPropertyValue(ownerField, entity);
                
            });
            
        }, field -> Collection.class.isAssignableFrom(field.getType()) &&
                field.getAnnotation(OneToMany.class) != null);

    }

}
