package com.Company.AccountService.presentationLayer.controllerPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private long employeeID;
    private String period;
    private Long salary;
}
