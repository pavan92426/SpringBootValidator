package com.loanapi.dev.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loanapi.dev.api.exception.EnumNamePattern;
import com.loanapi.dev.utils.LoanType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SubmitLoanRequest {
    @Range(min = 1000, max = 2000000)
    private int principalAmount;
    @Range(min = 5, max = 80)
    private int termMonths;
    @EnumNamePattern(regexp = "HOME|VEHICLE|PERSONAL",flags = Pattern.Flag.CASE_INSENSITIVE)
    private LoanType loanType;
    @Valid
    private Collateral collateral;
    @Valid
    private Customer customer;

    @Getter
    @Setter
    public static class Collateral {
        @NotBlank
        @Size(max=50)
        private String itemName;

        private int purchasedYear;
        @Positive
        private BigDecimal ItemValue;

    }

    @Getter
    @Setter
    public static class Customer {
        @NotEmpty
        private String name;
        @Past
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dob;
        @NotEmpty
        private String idNum;
        @Min(5000)
        private int income;

    }
}


