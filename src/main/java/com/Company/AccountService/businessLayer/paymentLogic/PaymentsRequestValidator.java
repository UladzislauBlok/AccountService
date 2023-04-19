package com.Company.AccountService.businessLayer.paymentLogic;

import com.Company.AccountService.persistenceLayer.crudRepository.UserRepository;
import com.Company.AccountService.presentationLayer.payment.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsRequestValidator {

    private final UserRepository userRepository;
    public boolean isValid(PaymentRequest paymentRequest) {
        String regexpEmail = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}";
        String regexpPeriod = "^((0[1-9])|[1-9]|1[0-2])-(19|20)[0-9]{2}$";

        if (!paymentRequest.getEmployee().matches(regexpEmail)) {
            return false;
        }
        if (!paymentRequest.getPeriod().matches(regexpPeriod)) {
            return false;
        }
        if (paymentRequest.getSalary() < 0) {
            return false;
        }
        if (userRepository.findByEmail(paymentRequest.getEmployee()).isEmpty()) {
            return false;
        }
        return true;
    }
}
