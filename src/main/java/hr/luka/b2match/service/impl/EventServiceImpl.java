package hr.luka.b2match.service.impl;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.User;
import hr.luka.b2match.data.model.dto.EventDTO;
import hr.luka.b2match.data.repository.EventRepository;
import hr.luka.b2match.service.EventService;
import hr.luka.b2match.service.UserService;
import hr.luka.b2match.service.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hr.luka.b2match.config.exception.ExceptionSuppliers.ENTITY_NOT_FOUND_EXCEPTION;
import static hr.luka.b2match.config.exception.ExceptionSuppliers.TIME_INCONSISTENT_EXCEPTION;

@Service
public class EventServiceImpl implements EventService {

    public final EventRepository eventRepository;
    public final UserService userService;
    public final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService, EventMapper eventMapper){
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event getEventById(Long id) {
        return this.eventRepository.findById(id).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
    }

    @Override
    public EventDTO getEventDTOById(Long id) {
        return this.eventMapper.fromEntity(this.eventRepository.findById(id).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION));
    }

    @Override
    public List<EventDTO> getAllEvents(){
        return this.eventRepository.findAll().stream().map(this.eventMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllUpcomingEvents() {
        List<Event> allByStartTimeAfter = this.eventRepository.findAllByStartTimeAfter(LocalDateTime.now());
        return allByStartTimeAfter.stream().map(this.eventMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = this.eventMapper.fromDto(eventDTO);
        Event save = this.eventRepository.save(event);
        if(eventDTO.getEndTime().isBefore(eventDTO.getStartTime())){
            throw TIME_INCONSISTENT_EXCEPTION.get();
        }
        return this.eventMapper.fromEntity(save);
    }

    @Override
    public EventDTO addUserToEvent(Long eventId, Long userId){
        User user = this.userService.findUserById(userId);
        Event event = this.eventRepository.findById(eventId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
        event.getParticipants().add(user);

        return this.eventMapper.fromEntity(this.eventRepository.save(event));
    }
}
