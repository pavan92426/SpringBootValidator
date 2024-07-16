package com.loanapi.dev.api.response;

import com.loanapi.dev.utils.LoanStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SubmitLoanResponse {
    private String customerName;
    private UUID loanID;
    private LoanStatus status;
}
