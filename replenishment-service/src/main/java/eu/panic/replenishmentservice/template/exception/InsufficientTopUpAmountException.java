package eu.panic.replenishmentservice.template.exception;

public class InsufficientTopUpAmountException extends RuntimeException{
    public InsufficientTopUpAmountException(String exMessage){
        super(exMessage);
    }
}
