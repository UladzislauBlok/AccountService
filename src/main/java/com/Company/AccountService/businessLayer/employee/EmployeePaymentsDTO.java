package com.Company.AccountService.businessLayer.employee;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "name",
        "lastname",
        "period",
        "salary"
})
public class EmployeePaymentsDTO {

    private String name;
    private String lastname;
    private String period;
    private Long salary;
}
