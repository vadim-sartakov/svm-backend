package svm.backend.datachange.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DATABASECHANGELOG")
public class DatabaseChangelog implements Serializable {
    
    @Id
    @Column(columnDefinition = "nvarchar(255)", name = "ID")
    private String id;
    
    @Column(columnDefinition = "nvarchar(255)", name = "AUTHOR")
    private String author = "DataChangeBean";
    
    @Column(columnDefinition = "nvarchar(255)", name = "FILENAME")
    private String filename = "DataChangeBean";
    
    @Column(name = "DATEEXECUTED")
    private ZonedDateTime dateExecuted = ZonedDateTime.now();
    
    @Column(name = "ORDEREXECUTED")
    private Integer orderExecuted = -1;
    
    @Column(columnDefinition = "nvarchar(10)", name = "EXECTYPE")
    private String execType = "EXECUTED";
    
    public static DatabaseChangelog of(String id) {
        DatabaseChangelog databaseChangelog = new DatabaseChangelog();
        databaseChangelog.id = id;
        return databaseChangelog;
    }
    
}
