package com.Company.AccountService.businessLayer.employee;


import com.Company.AccountService.businessLayer.user.User;
import jakarta.persistence.*;
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

    @ManyToOne
    private User employee;
    private String period;
    private Long salary;
}
