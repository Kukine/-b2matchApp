package hr.luka.b2match.config.exception;

public class MeetingScheduleException extends RuntimeException {

    private static String MEETING_SCHEDULE_MESSAGE = "Meeting can not be scheduled due to schedule inconsistency";


    public MeetingScheduleException(){
        super(MEETING_SCHEDULE_MESSAGE);
    }
}
