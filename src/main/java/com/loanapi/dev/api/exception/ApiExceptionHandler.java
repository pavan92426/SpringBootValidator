package com.loanapi.dev.api.exception;

import com.loanapi.dev.api.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    ResponseEntity<ExceptionResponse> handleArithmeticExp(ArithmeticException e) {
        var response = ExceptionResponse.builder()
                .message("arithmetic exception")
                .summary(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> genericException(Exception e) {
        var response = ExceptionResponse.builder()
                .message(e.getClass().toString() + " " + e.getMessage())
                .summary("exception")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(LoanBusinessException.class)
    ResponseEntity<ExceptionResponse> getCustomExp(LoanBusinessException e) {
        var response = ExceptionResponse.builder()
                .summary("loan business process exception")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(LoanOwnerException.class)
    ResponseEntity<ExceptionResponse> getLoanOwnerExp(LoanOwnerException e){
        var response = ExceptionResponse.builder()
                .summary("loan business owner exception")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<ExceptionResponse> getFieldValidationError(MethodArgumentNotValidException e){

        var message = e.getFieldErrors().stream().map(f -> f.getField() +" Value = ("+f.getRejectedValue() + ") " + f.getDefaultMessage()+". ")
                .collect(Collectors.joining());

        var response = ExceptionResponse.builder()
                .summary("Invalid input arguments")
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}
