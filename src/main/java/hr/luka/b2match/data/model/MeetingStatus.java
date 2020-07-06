package hr.luka.b2match.data.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingStatus {
    CANCELED(0), PENDING(1), SCHEDULED(2);

    private int value;
}
