package svm.backend.samples.shop.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class OrderRow implements Serializable {
    
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
