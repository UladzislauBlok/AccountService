package com.Company.AccountService.businessLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class CustomUNAUTHORIZED_Exception extends RuntimeException{
    public CustomUNAUTHORIZED_Exception (String msg) {
        super(msg);
    }
}
