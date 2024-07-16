package com.loanapi.dev.repository;

import com.loanapi.dev.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Optional<Loan> findByLoanIdAndCreatedBy(UUID loanId, String partnerSecret);
}
