package eu.panic.replenishmentservice.template.exception;

public class PaymentsExistsException extends RuntimeException{
    public PaymentsExistsException(String exMessage){
        super(exMessage);
    }
}
