package svm.backend.dao.entity;

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
    @Column(name = "ID")
    private String id;
    
    @Column(name = "AUTHOR")
    private String author;
    
    @Column(name = "FILENAME")
    private String filename = "classpath:db/changelog/changelog-master.xml";
    
    @Column(name = "DATEEXECUTED")
    private ZonedDateTime dateExecuted = ZonedDateTime.now();
    
    @Column(name = "ORDEREXECUTED")
    private Integer orderExecuted = 1;
    
    @Column(name = "EXECTYPE")
    private String execType = "EXECUTED";
    
    public static DatabaseChangelog of(String id, String author) {
        DatabaseChangelog databaseChangelog = new DatabaseChangelog();
        databaseChangelog.id = id;
        databaseChangelog.author = author;
        return databaseChangelog;
    }
    
}
