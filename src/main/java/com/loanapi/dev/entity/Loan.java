package com.loanapi.dev.entity;

import com.loanapi.dev.utils.LoanStatus;
import com.loanapi.dev.utils.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID loanId;

    private int principalAmount;
    private LoanType loanType;

    private int termMonths;

    @Column(length = 50)
    private String collateralName;

    @Column(length = 8)
    private BigDecimal collateralValue;

    private int collateralManufacturingYear;

    @Column(length = 50)
    private String customerName;

    private LocalDate customerBirthDate;

    private int customerMonthlyIncome;

    @Column(length = 50)
    private String customerIdNumber;

    @Column(length = 50)
    private String createdBy;

    private LoanStatus status;

    private int loanInterest;

    private int monthlyInstallment;
}
