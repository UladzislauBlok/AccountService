package com.Company.AccountService.businessLayer.paymentLogic;


import com.Company.AccountService.businessLayer.employee.EmployeePayment;
import com.Company.AccountService.persistenceLayer.crudRepository.EmployeePaymentsRepository;
import com.Company.AccountService.presentationLayer.payment.PaymentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final EmployeePaymentsRepository employeePaymentsRepository;
    private final PaymentsRequestValidator paymentsRequestValidator;

    @Transactional
    public void addPayments(List<PaymentRequest> paymentRequestList) {
        for (PaymentRequest paymentRequest : paymentRequestList) {
            if (!paymentsRequestValidator.isValid(paymentRequest)) {
                throw new IncorrectPaymentDataException();
            } else if (employeePaymentsRepository.findByEmployeeAndPeriod(paymentRequest.getEmployee(), paymentRequest.getPeriod()).isPresent()) {
                throw new PaymentDataEarlierExist();
            }
            var employeePayment = EmployeePayment.builder()
                    .employee(paymentRequest.getEmployee())
                    .period(paymentRequest.getPeriod())
                    .salary(paymentRequest.getSalary())
                    .build();

            employeePaymentsRepository.save(employeePayment);
        }
    }

    public void updatePayments(PaymentRequest paymentRequest) {
        if (!paymentsRequestValidator.isValid(paymentRequest)) {
            throw new IncorrectPaymentDataException();
        } else if (employeePaymentsRepository.findByEmployeeAndPeriod(paymentRequest.getEmployee(), paymentRequest.getPeriod()).isEmpty()) {
            throw new PaymentDataDoesntExist();
        }
        var employeePayment = employeePaymentsRepository.findByEmployeeAndPeriod(paymentRequest.getEmployee(), paymentRequest.getPeriod()).orElseThrow();
        employeePayment.setSalary(paymentRequest.getSalary());

        employeePaymentsRepository.save(employeePayment);
    }
}
