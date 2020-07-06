package hr.luka.b2match.config.exception;

import hr.luka.b2match.data.model.Event;

public class EventParticipationException extends RuntimeException{

    public EventParticipationException(){
        super("ERROR: Sending invitation for meeting held at an event the recipient isn't participating in");
    }
}
