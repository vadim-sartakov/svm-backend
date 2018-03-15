/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.FieldSet;
import svm.backend.data.entity.validator.UniqueValues.Field;

@Data
@UniqueValues(fieldSets = @FieldSet({ @Field("firstName"), @Field("lastName") }))
@NoArgsConstructor
@Entity
public class MultipleFieldEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;

    public MultipleFieldEntity(String firstName) {
        this.firstName = firstName;
    }
    
}
