package svm.backend.dao.entity;

import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import svm.backend.dao.entity.contact.Email;
import svm.backend.dao.entity.contact.PhoneNumber;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedEntityGraph(name = "users.overview", attributeNodes = {
    @NamedAttributeNode("roles"),
    @NamedAttributeNode("emails"),
    @NamedAttributeNode("phoneNumbers")
})
public class User extends UUIDEntity {
        
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    @Builder.Default
    private ZonedDateTime creationDate = ZonedDateTime.now();
    private ZonedDateTime bannedTill;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean banned = Boolean.TRUE;
    
    @Singular
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Email> emails;
    
    @Singular
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;
    
    @Singular
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> roles;

    @Column(nullable = false, length = 50)
    private String password;
    
    public boolean isInRole(UserRole roleToFind) {
        for (UserRole currentRole : roles)
            if (currentRole.equals(roleToFind))
                return true;
        return false;
    }
    
    public void setName(String name) {
        this.name = name.toLowerCase();
    }
            
}
