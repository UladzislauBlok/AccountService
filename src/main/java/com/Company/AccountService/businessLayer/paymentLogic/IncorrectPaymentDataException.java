package com.Company.AccountService.businessLayer.paymentLogic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Incorrect payment data")
public class IncorrectPaymentDataException extends RuntimeException { }
