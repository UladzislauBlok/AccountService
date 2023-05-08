package com.Company.AccountService.businessLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomBAD_REQUEST_Exception extends RuntimeException{
    public CustomBAD_REQUEST_Exception (String message) {
        super(message);
    }
}
