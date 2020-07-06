package hr.luka.b2match.config.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(){
        super("Entity with given ID doesn't exist");
    }

    public EntityNotFoundException(String message){
        super(message);
    }
}
