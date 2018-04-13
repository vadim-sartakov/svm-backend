package svm.backend.data.jpa.migration;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.migration.model.Migration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "MIGRATIONS")
@Entity
public class JpaMigration implements Migration, Serializable {
    @Id
    @NotEmpty
    private String id;
    private Integer executionOrder;    
}
