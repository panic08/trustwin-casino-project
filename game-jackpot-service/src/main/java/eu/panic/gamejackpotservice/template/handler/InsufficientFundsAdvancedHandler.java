package eu.panic.gamejackpotservice.template.handler;

import eu.panic.gamejackpotservice.template.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InsufficientFundsAdvancedHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientFundsException.class)
    public String handleInsufficientFundsException(InsufficientFundsException insufficientFundsException){
        return insufficientFundsException.getMessage();
    }
}
