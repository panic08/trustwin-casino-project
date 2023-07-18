package eu.panic.gamecrashservice.template.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exMessage){
        super(exMessage);
    }
}
