package com.loanapi.dev.controller;

import com.loanapi.dev.api.exception.LoanBusinessException;
import com.loanapi.dev.api.exception.LoanOwnerException;
import com.loanapi.dev.api.request.SubmitLoanRequest;
import com.loanapi.dev.api.response.SubmitLoanResponse;
import com.loanapi.dev.entity.Loan;
import com.loanapi.dev.service.ILoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
public class LoanController {

    private static final String HTTP_HEADER_PARTNER_SECRET = "partner-secret";

    @Autowired
    private ILoanService loanService;

    @PostMapping(value = "/api/loan", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubmitLoanResponse> submitLoan(
            @RequestBody @Valid SubmitLoanRequest loanRequest,
            @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET, required = true) String partnerSecret) {

       /* if (loanRequest.getPrincipalAmount() < 100 || loanRequest.getPrincipalAmount() > 99999) {
            throw new LoanBusinessException("Loan principal amount must between 100-99999, currently : "
                    + loanRequest.getPrincipalAmount());
        }
        var age = Period.between(
                loanRequest.getCustomer().getDob(),
                LocalDate.now()
        ).getYears();

        if (age < 18 || age > 70) {
            throw new LoanBusinessException(
                    "Customer age must between 18 and 70, currently : "
                            + age);
        }*/

        var savedLoan = loanService.save(loanRequest, partnerSecret);

        var submitLoanResponse = SubmitLoanResponse.builder()
                .loanID(savedLoan.getLoanId())
                .customerName(savedLoan.getCustomerName())
                .status(savedLoan.getStatus()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(submitLoanResponse);
    }

    @GetMapping(value = "/api/loan", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Loan> findLoan(@RequestParam(name = "loan_id", required = true) String loanId,
                                  @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET, required = true) String partnerSecret) {
        var existingLoan = loanService.find(loanId, partnerSecret);

        if(existingLoan == null) {
            if(loanService.loanIdExists(loanId)){
                throw new LoanOwnerException("You cannot access " + loanId);
            } else {
                throw new LoanOwnerException("Loan " + loanId + " does not exist");
            }
        }

        return ResponseEntity.ok().body(existingLoan);
    }

    @GetMapping
    public String testAPI() {
        return "Hellooo Loan Application !!";
    }
}
