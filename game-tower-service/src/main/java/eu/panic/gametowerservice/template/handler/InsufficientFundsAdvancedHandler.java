package eu.panic.gametowerservice.template.handler;

import eu.panic.gametowerservice.template.exception.InsufficientFundsException;
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
        /*
                If we return ResponseEntity<String> and add utf-8 encoding to Content-Type then the UTF-8 encoded text will be returned.
            Right now, the text is returned with encoding ISO-8859-1
        */

        return insufficientFundsException.getMessage();
    }
}
