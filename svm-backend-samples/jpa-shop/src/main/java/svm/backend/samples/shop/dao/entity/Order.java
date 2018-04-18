package svm.backend.samples.shop.dao.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "OrderDocument")
@Table(name = "ORDERS")
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderRow> products;
    
}
