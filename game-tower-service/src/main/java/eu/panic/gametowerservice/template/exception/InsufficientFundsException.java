package eu.panic.gametowerservice.template.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String exMessage){
        super(exMessage);
    }
}
