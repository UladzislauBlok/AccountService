package com.Company.AccountService.businessLayer.paymentLogic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Payment data already exist")
public class PaymentDataEarlierExistException extends RuntimeException{ }
