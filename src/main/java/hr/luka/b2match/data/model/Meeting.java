package hr.luka.b2match.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hr.luka.b2match.data.model.dto.MeetingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    private String location;

    private Integer status;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_meetings",
            joinColumns = { @JoinColumn(name = "meeting_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private List<User> participants;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "meeting"
    )
    private List<Invitation> invitations;

    public boolean isConcurrent(Meeting m2){
        if((this.getStartTime().isAfter(m2.getStartTime()) && this.getStartTime().isBefore(m2.getEndTime()))
                ||(this.getEndTime().isAfter(m2.getStartTime()) && this.getEndTime().isBefore(m2.getEndTime()))){
            return true;
        }
        return false;
    }

    public boolean isConcurrent(MeetingDTO m2){
        if((this.getStartTime().isAfter(m2.getStartTime()) && this.getStartTime().isBefore(m2.getEndTime()))
                ||(this.getEndTime().isAfter(m2.getStartTime()) && this.getEndTime().isBefore(m2.getEndTime()))){
            return true;
        }
        return false;
    }

}
