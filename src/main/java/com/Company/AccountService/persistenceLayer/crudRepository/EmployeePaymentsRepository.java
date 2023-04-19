package com.Company.AccountService.persistenceLayer.crudRepository;

import com.Company.AccountService.businessLayer.employee.EmployeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeePaymentsRepository extends JpaRepository<EmployeePayment, Integer> {

    List<EmployeePayment> findAllByEmployee(String employee);

    Optional<EmployeePayment> findByEmployeeAndPeriod(String employee, String period);
}
