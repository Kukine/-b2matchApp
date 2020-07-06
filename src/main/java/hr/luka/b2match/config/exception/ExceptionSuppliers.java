package hr.luka.b2match.config.exception;

import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class ExceptionSuppliers {

    public static final Supplier<EntityNotFoundException> ENTITY_NOT_FOUND_EXCEPTION = EntityNotFoundException::new;
    public static final Supplier<MeetingScheduleException> MEETING_SCHEDULE_EXCEPTION = MeetingScheduleException::new;
    public static final Supplier<EventParticipationException> EVENT_PARTICIPATION_EXCEPTION = EventParticipationException::new;
    public static final Supplier<ExistsByEmailException> EXISTS_BY_EMAIL_EXCEPTION = ExistsByEmailException::new;
    public static final Supplier<TimeInconsistentException> TIME_INCONSISTENT_EXCEPTION = TimeInconsistentException::new;
}
