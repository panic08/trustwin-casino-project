package eu.panic.gameovergoservice.template.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String exMessage){
        super(exMessage);
    }
}
