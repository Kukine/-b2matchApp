package hr.luka.b2match.config.exception;

public class ExistsByEmailException extends RuntimeException{

    public ExistsByEmailException(){
        super("ERROR: User with given email already exists");
    }

}
