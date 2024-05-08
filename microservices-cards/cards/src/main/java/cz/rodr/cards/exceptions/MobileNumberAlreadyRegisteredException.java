package cz.rodr.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MobileNumberAlreadyRegisteredException extends RuntimeException{
    public MobileNumberAlreadyRegisteredException(String message) {
        super(message);
    }
}
