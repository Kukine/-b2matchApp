package hr.luka.b2match.service.mapper;

import hr.luka.b2match.data.model.Invitation;
import hr.luka.b2match.data.model.dto.InvitationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper implements AbstractDTOMapper<Invitation, InvitationDTO> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public Invitation fromDto(InvitationDTO dto) {
        return Invitation.builder()
                .id(dto.getId())
                .sender(this.userMapper.fromDto(dto.getSender()))
                .recipient(this.userMapper.fromDto(dto.getRecipient()))
                .status(dto.getStatus())
                .meeting(this.meetingMapper.fromDto(dto.getMeeting()))
                .build();
    }

    @Override
    public InvitationDTO fromEntity(Invitation entity) {
        return InvitationDTO.builder()
                .id(entity.getId())
                .sender(this.userMapper.fromEntity(entity.getSender()))
                .recipient(this.userMapper.fromEntity(entity.getRecipient()))
                .meeting(this.meetingMapper.fromEntity(entity.getMeeting()))
                .status(entity.getStatus())
                .build();
    }
}
