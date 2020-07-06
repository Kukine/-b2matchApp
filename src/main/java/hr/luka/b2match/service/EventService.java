package hr.luka.b2match.service;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.dto.EventDTO;

import java.util.List;

public interface EventService {

    Event getEventById(Long id);

    EventDTO getEventDTOById(Long id);

    List<EventDTO> getAllEvents();

    List<EventDTO> getAllUpcomingEvents();

    EventDTO createEvent(EventDTO event);

    EventDTO addUserToEvent(Long eventId, Long userId);
}
