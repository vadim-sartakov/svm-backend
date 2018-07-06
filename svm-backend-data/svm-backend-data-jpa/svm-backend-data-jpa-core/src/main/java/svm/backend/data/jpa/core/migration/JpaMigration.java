package svm.backend.data.jpa.core.migration;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.core.migration.model.Migration;

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
}
