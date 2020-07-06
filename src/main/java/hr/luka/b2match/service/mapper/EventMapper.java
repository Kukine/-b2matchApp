package hr.luka.b2match.service.mapper;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper implements  AbstractDTOMapper<Event, EventDTO>{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Event fromDto(EventDTO dto) {
        return Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .location(dto.getLocation())
                .build();
    }

    @Override
    public EventDTO fromEntity(Event entity) {
        return EventDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .endTime(entity.getEndTime())
                .startTime(entity.getStartTime())
                .participants(entity.getParticipants() != null ? entity.getParticipants().stream().map(this.userMapper::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }

}
