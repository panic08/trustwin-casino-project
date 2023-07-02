package eu.panic.authservice.template.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exMessage){
        super(exMessage);
    }
}
