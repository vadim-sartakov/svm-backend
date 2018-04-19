package svm.backend.samples.shop.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.jpa.entity.Identifiable;

@Data
@Entity(name = "OrderDocument")
@Table(name = "ORDERS")
public class Order implements Serializable, Identifiable {
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false)
    private String number;
    
    @NotNull
    @Column(nullable = false)
    private ZonedDateTime orderDate;
    
    @NotNull
    @ManyToOne(optional = false)
    private Stock stock;
    
    @Valid
    @ElementCollection
    @CollectionTable(name = "ORDER_PRODUCTS")
    private List<OrderRow> products;
        
}
