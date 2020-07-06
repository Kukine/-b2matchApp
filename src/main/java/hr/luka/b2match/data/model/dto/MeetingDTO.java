package hr.luka.b2match.data.model.dto;

import hr.luka.b2match.data.model.Meeting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDTO{

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    private Long eventId;

    private Long organizerId;

    private Integer status;

}
