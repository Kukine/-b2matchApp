package hr.luka.b2match.service.mapper;

import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.dto.MeetingDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MeetingMapper implements AbstractDTOMapper<Meeting, MeetingDTO> {

    @Override
    public Meeting fromDto(MeetingDTO dto) {
        return Meeting.builder()
                .location(dto.getLocation())
                .startTime(dto.getStartTime())
                .status(dto.getStatus())
                .endTime(dto.getEndTime())
                .participants(new ArrayList<>())
                .invitations(new ArrayList<>())
                .build();
    }

    @Override
    public MeetingDTO fromEntity(Meeting entity) {
        return MeetingDTO.builder()
                .id(entity.getId())
                .organizerId(entity.getOrganizer().getId())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .eventId(entity.getEvent().getId())
                .build();
    }

}
