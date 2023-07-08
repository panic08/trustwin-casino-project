package eu.panic.gamediceservice.template.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String exMessage){
        super(exMessage);
    }
}
