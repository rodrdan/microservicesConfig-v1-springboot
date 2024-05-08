package cz.rodr.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(String fieldName, String fieldValue) {
        super(String.format("Loan with %s %s was not found.", fieldName, fieldValue));
    }
}
