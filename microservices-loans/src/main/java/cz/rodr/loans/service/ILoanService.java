package cz.rodr.loans.service;

import cz.rodr.loans.dto.LoanDto;
import cz.rodr.loans.entity.Loan;

public interface ILoanService {

    /**
     *
     * @param mobileNumber - Mobile number to register with Loan
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Mobile number registered with Loan
     * @return LoanDto object
     */
    LoanDto getLoanDetails(String mobileNumber);

    /**
     *
     * @param loanDto - LoanDto object of existing Loan
     * @return boolean indicating if Loan was successfully updated or not
     */
    boolean updateLoan(LoanDto loanDto);

    /**
     *
     * @param mobileNumber - Mobile number registered with Loan
     * @return boolean indicating if Loan was successfully deleted or not
     */
    boolean deleteLoan(String mobileNumber);
}
