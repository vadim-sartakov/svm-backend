/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.core.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.core.validator.UniqueValues;
import svm.backend.data.core.validator.UniqueValues.FieldSet;
import svm.backend.data.core.validator.UniqueValues.Field;

@Entity
@Data
@UniqueValues(groups = TestGroup.class,
        fieldSets = @FieldSet({
            @Field("firstName"),
            @Field(value = "lastName", ignoreCaseExpr = "${ignoreCaseParam}")
        }))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultipleFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
}
