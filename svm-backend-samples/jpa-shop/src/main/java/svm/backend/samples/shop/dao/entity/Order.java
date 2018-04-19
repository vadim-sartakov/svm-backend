package svm.backend.samples.shop.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.format.annotation.DateTimeFormat;
import svm.backend.data.jpa.entity.Creatable;
import svm.backend.data.jpa.entity.Identifiable;

@Data
@Entity(name = "OrderDocument")
@Table(name = "ORDERS")
@NamedEntityGraph(name = "overview", attributeNodes = @NamedAttributeNode("stock"))
public class Order implements Serializable, Identifiable, Creatable {
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false)
    private String number;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private ZonedDateTime createdAt;
    
    @NotNull
    @ManyToOne(optional = false)
    private Stock stock;
    
    @Valid
    @ElementCollection
    @CollectionTable(name = "ORDERS_PRODUCTS")
    private List<ProductRow> products;
    
    @Data
    @Embeddable
    public static class ProductRow implements Serializable {

        @Valid
        @NotNull
        @ManyToOne(optional = false)
        private Product product;

        @NotNull
        @Column(nullable = false)
        private BigDecimal price;

        @NotNull
        @Column(nullable = false)
        private BigDecimal quantity;

    }
    
    @Projection(name = "list", types = Order.class)
    public interface Overview {
        String getNumber();
        ZonedDateTime getCreatedAt();
        Stock getStock();
    }
        
}
