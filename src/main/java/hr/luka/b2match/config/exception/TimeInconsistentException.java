package hr.luka.b2match.config.exception;

public class TimeInconsistentException extends RuntimeException{

    public TimeInconsistentException(){
        super("ERROR: End time can not be before start time");
    }
}
