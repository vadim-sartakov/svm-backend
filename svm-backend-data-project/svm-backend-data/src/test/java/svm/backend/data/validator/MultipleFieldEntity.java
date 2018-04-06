/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.validator.UniqueValues;
import svm.backend.data.validator.UniqueValues.FieldSet;
import svm.backend.data.validator.UniqueValues.Field;

@Data
@UniqueValues(fieldSets = @FieldSet({ @Field("firstName"), @Field(value = "lastName", ignoreCaseExpr = "${ignoreCaseParam}") }))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultipleFieldEntity {
    private String firstName;
    private String lastName;
}
