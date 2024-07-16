package com.loanapi.dev.api.exception;


public class LoanBusinessException extends RuntimeException {
    public LoanBusinessException(String message) {
        super(message);
    }
}
