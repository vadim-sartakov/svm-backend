package svm.backend.dao.entity.contact;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMAIL")
public class Email extends Contact {

}
