package eu.panic.notificationservice.template.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String  exMessage){
        super(exMessage);
    }
}
