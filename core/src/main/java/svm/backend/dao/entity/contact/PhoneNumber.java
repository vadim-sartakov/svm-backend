package svm.backend.dao.entity.contact;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE_NUMBER")
public class PhoneNumber extends Contact {
    
}
