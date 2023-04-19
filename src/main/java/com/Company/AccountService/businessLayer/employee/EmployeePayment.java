package com.Company.AccountService.businessLayer.employee;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_payments")
public class EmployeePayment {

    @Id
    @GeneratedValue
    private long id;
    private String employee;
    private String period;
    private Long salary;
}
