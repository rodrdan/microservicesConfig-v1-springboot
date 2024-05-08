package cz.rodr.loans.service.impl;

import cz.rodr.loans.constants.LoanConstants;
import cz.rodr.loans.dto.LoanDto;
import cz.rodr.loans.entity.Loan;
import cz.rodr.loans.exception.LoanAlreadyExistsException;
import cz.rodr.loans.exception.LoanNotFoundException;
import cz.rodr.loans.mapper.LoanMapper;
import cz.rodr.loans.repository.LoanRepository;
import cz.rodr.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private LoanRepository loanRepository;
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> loan = loanRepository.findByMobileNumber(mobileNumber);
        if (loan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists with mobile number " + mobileNumber);
        }
        Loan newLoan = createNewLoan(mobileNumber);
        loanRepository.save(newLoan);
    }

    @Override
    public LoanDto getLoanDetails(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new LoanNotFoundException("mobile number", mobileNumber));
        return LoanMapper.mapToLoanDto(loan, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(() ->
                new LoanNotFoundException("loan number", loanDto.getLoanNumber()));
        LoanMapper.mapToLoan(loanDto, loan);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new LoanNotFoundException("mobile number", mobileNumber));
        loanRepository.delete(loan);
        return true;
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        newLoan.setMobileNumber(mobileNumber);
        long randomNumber = 100_000_000_000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomNumber));
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
