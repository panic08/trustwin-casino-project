package eu.panic.gamejackpotservice.template.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String exMessage){
        super(exMessage);
    }
}
