package com.loanapi.dev.service;

import com.loanapi.dev.api.request.SubmitLoanRequest;
import com.loanapi.dev.entity.Loan;

public interface ILoanService {

    Loan save(SubmitLoanRequest request, String partnerSecret);

    Loan find(String loanId, String partnerSecret);

    boolean loanIdExists(String loanId);
}
