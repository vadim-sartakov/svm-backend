/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.validator;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svm.backend.data.validator.UniqueValues;
import svm.backend.data.validator.UniqueValues.Field;

@Data
@UniqueValues(fields = {
    @Field("stringIgnoreCase"),
    @Field(value = "stringExact", ignoreCaseExpr = "${ignoreCaseParam}"),
    @Field("uniqueNumber")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleFieldEntity {
    private String stringIgnoreCase;
    private String stringExact;
    private Integer uniqueNumber;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
