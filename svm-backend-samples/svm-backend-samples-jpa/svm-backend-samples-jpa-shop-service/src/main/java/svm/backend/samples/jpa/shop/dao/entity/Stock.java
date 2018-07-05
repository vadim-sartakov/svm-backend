package svm.backend.samples.jpa.shop.dao.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.config.Projection;
import svm.backend.data.jpa.core.entity.Identifiable;

@Data
@Entity
@Table(name = "STOCKS")
public class Stock implements Identifiable, Serializable {
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.core.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    @Projection(name = "overview", types = Stock.class)
    public interface Overview {
        String getName();
    }
    
}
