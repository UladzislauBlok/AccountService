package com.Company.AccountService.businessLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CustomNOT_FOUND_Exception extends RuntimeException{
    public CustomNOT_FOUND_Exception (String message) {
        super(message);
    }
}
