package com.Company.AccountService.businessLayer.paymentLogic;


import com.Company.AccountService.businessLayer.employee.EmployeePayment;
import com.Company.AccountService.businessLayer.employee.EmployeePaymentsDTO;
import com.Company.AccountService.businessLayer.exception.CustomBAD_REQUEST_Exception;
import com.Company.AccountService.persistenceLayer.crudRepository.EmployeePaymentsRepository;
import com.Company.AccountService.persistenceLayer.crudRepository.UserRepository;
import com.Company.AccountService.presentationLayer.payment.PaymentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final UserRepository userRepository;
    private final EmployeePaymentsRepository employeePaymentsRepository;
    private final PaymentsRequestValidator paymentsRequestValidator;

    @Transactional
    public void addPayments(List<PaymentRequest> paymentRequestList) {
        for (PaymentRequest paymentRequest : paymentRequestList) {
            if (!paymentsRequestValidator.isValid(paymentRequest)) {
                throw new CustomBAD_REQUEST_Exception("Incorrect payment data");
            } else if (employeePaymentsRepository.findByEmployeeAndPeriod(paymentRequest.getEmployee(), paymentRequest.getPeriod()).isPresent()) {
                throw new CustomBAD_REQUEST_Exception("Payment data already exist");
            }
            var employeePayment = EmployeePayment.builder()
                    .employee(paymentRequest.getEmployee())
                    .period(paymentRequest.getPeriod())
                    .salary(paymentRequest.getSalary())
                    .build();

            employeePaymentsRepository.save(employeePayment);
        }
    }

    public void updatePayment(PaymentRequest paymentRequest) {
        if (!paymentsRequestValidator.isValid(paymentRequest)) {
            throw new CustomBAD_REQUEST_Exception("Incorrect payment data");
        }  try {
            var employeePayment = employeePaymentsRepository.findByEmployeeAndPeriod(paymentRequest.getEmployee(), paymentRequest.getPeriod()).orElseThrow();
            employeePayment.setSalary(paymentRequest.getSalary());
            employeePaymentsRepository.save(employeePayment);
        } catch (Exception e){
            throw new CustomBAD_REQUEST_Exception("Payment data doesn't exist");
        }
    }

    public List<EmployeePaymentsDTO> getPaymentList(Authentication authentication) {
        String emailEmployee = authentication.getName();
        List<EmployeePayment> payments = employeePaymentsRepository.findAllByEmployee(emailEmployee);
        var user = userRepository.findByEmail(emailEmployee).orElseThrow();

        List<EmployeePaymentsDTO> paymentsDTOList = new ArrayList<>(payments.size());
        for (EmployeePayment payment : payments) {
            paymentsDTOList.add(
                    new EmployeePaymentsDTO(
                            user.getName(),
                            user.getLastname(),
                            payment.getPeriod(),
                            payment.getSalary()
                    )
            );
        }
        return paymentsDTOList;
    }

    public EmployeePaymentsDTO getPayment(Authentication authentication, String period) {
        String emailEmployee = authentication.getName();
        if (employeePaymentsRepository.findByEmployeeAndPeriod(emailEmployee, period).isEmpty()){
            return null;
        }
        var payments = employeePaymentsRepository.findByEmployeeAndPeriod(emailEmployee, period).orElseThrow();
        var user = userRepository.findByEmail(emailEmployee).orElseThrow();

        return new EmployeePaymentsDTO(
                user.getName(),
                user.getLastname(),
                payments.getPeriod(),
                payments.getSalary()
        );
    }
}
