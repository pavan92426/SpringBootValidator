package com.loanapi.dev.service;

import com.loanapi.dev.api.request.SubmitLoanRequest;
import com.loanapi.dev.entity.Loan;
import com.loanapi.dev.repository.LoanRepository;
import com.loanapi.dev.utils.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoanServiceImpl implements ILoanService {
    @Autowired
    private LoanRepository repository;

    @Override
    public Loan save(SubmitLoanRequest request, String partnerSecret) {
        var loanInterest = (int) Math.ceil(request.getPrincipalAmount() * request.getTermMonths() * 0.01);
        var monthlyInstallment =
                (int) Math.ceil((request.getPrincipalAmount() + loanInterest) / request.getTermMonths());
        var loanEntity = map(request);
        loanEntity.setLoanInterest(loanInterest);
        loanEntity.setMonthlyInstallment(monthlyInstallment);
        loanEntity.setCreatedBy(partnerSecret);
        loanEntity.setStatus(LoanStatus.PENDING);
        return repository.save(loanEntity);
    }

    @Override
    public Loan find(String loanId, String partnerSecret) {
        return repository.findByLoanIdAndCreatedBy(UUID.fromString(loanId), partnerSecret).orElse(null);
    }

    private Loan map(SubmitLoanRequest request) {
        var loan = new Loan();
        loan.setPrincipalAmount(request.getPrincipalAmount());
        loan.setTermMonths(request.getTermMonths());
        loan.setLoanType(request.getLoanType());
        loan.setCollateralName(request.getCollateral().getItemName());
        loan.setCollateralValue(request.getCollateral().getItemValue());
        loan.setCollateralManufacturingYear(request.getCollateral().getPurchasedYear());
        loan.setCustomerName(request.getCustomer().getName());
        loan.setCustomerIdNumber(request.getCustomer().getIdNum());
        loan.setCustomerBirthDate(request.getCustomer().getDob());
        loan.setCustomerMonthlyIncome(request.getCustomer().getIncome());
        return loan;
    }

    public boolean loanIdExists(String loanId){
        return repository.existsById(UUID.fromString(loanId));
    }
}
