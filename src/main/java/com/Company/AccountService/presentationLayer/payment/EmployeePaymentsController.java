package com.Company.AccountService.presentationLayer.payment;

import com.Company.AccountService.businessLayer.paymentLogic.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

}
