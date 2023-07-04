package eu.panic.replenishmentservice.template.handler;

import eu.panic.replenishmentservice.template.exception.InsufficientTopUpAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InsufficientTopUpAmountAdvancedHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientTopUpAmountException.class)
    private String handleInvalidCredentialsException(InsufficientTopUpAmountException insufficientTopUpAmountException){
        /*
                If we return ResponseEntity<String> and add utf-8 encoding to Content-Type then the UTF-8 encoded text will be returned.
            Right now, the text is returned with encoding ISO-8859-1
        */

        return insufficientTopUpAmountException.getMessage();
    }
}
