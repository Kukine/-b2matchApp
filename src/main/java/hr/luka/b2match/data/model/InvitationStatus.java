package hr.luka.b2match.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvitationStatus {
    DECLINED(0), PENDING(1), ACCEPTED(2);

    private int value;
}
