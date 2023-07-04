package eu.panic.replenishmentservice.template.handler;

import eu.panic.replenishmentservice.template.exception.PaymentsExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentsExistsAdvancedHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PaymentsExistsException.class)
    private String handlePaymentsExistsException(PaymentsExistsException paymentsExistsException){
        /*
                If we return ResponseEntity<String> and add utf-8 encoding to Content-Type then the UTF-8 encoded text will be returned.
            Right now, the text is returned with encoding ISO-8859-1
        */

        return paymentsExistsException.getMessage();
    }
}
