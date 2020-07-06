package hr.luka.b2match.data.model.dto;

import hr.luka.b2match.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InvitationDTO {

    private Long id;

    private UserDTO sender;

    private UserDTO recipient;

    private MeetingDTO meeting;

    private Integer status;

}
