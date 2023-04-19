package com.Company.AccountService.presentationLayer.payment;

import com.Company.AccountService.businessLayer.paymentLogic.IncorrectPaymentDataException;
import com.Company.AccountService.businessLayer.paymentLogic.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class EmployeePaymentsController {

    private final PaymentsService paymentsService;

    @PostMapping("/acct/payments")
    public ResponseEntity<Map<String , String>> addPayments(
            @RequestBody
            List<PaymentRequest> paymentRequestList
    ) {
        paymentsService.addPayments(paymentRequestList);
        return ResponseEntity.ok(Map.of("status", "Added successfully!"));
    }

    @PutMapping("/acct/payments")
    public ResponseEntity<Map<String , String>> updatePayments(
            @RequestBody PaymentRequest paymentRequest
    ) {
        paymentsService.updatePayments(paymentRequest);
        return ResponseEntity.ok(Map.of("status", "Updated successfully!"));
    }

    @GetMapping("/empl/payment")
    public Object getPaymentList(
            @RequestParam(required = false) String period,
            Authentication authentication
    ) {
        if (period == null) {
            return paymentsService.getPaymentList(authentication);
        } else if (period.matches("^((0[1-9])|1[0-2])-(19|20)[0-9]{2}$")) {
            return paymentsService.getPayment(authentication, period);
        } else {
            throw new IncorrectPaymentDataException();
        }
    }
}
