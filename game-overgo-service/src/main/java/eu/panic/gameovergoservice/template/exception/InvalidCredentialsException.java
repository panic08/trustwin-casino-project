package eu.panic.gameovergoservice.template.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exMessage){
        super(exMessage);
    }
}
