/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.entity;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.FieldSet;
import svm.backend.data.entity.validator.UniqueValues.Field;

@Data
@UniqueValues(fieldSets = @FieldSet({ @Field("firstName"), @Field(value = "lastName", ignoreCaseExpr = "${ignoreCaseParam}") }))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MultipleFieldEntity extends BaseEntity {
    private String firstName;
    private String lastName;
}
