package hr.luka.b2match.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String username;

    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Organization organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    //    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "user_events",
//            joinColumns = { @JoinColumn(name = "user_id") },
//            inverseJoinColumns = { @JoinColumn(name = "event_id")}
//    )
//    private List<Event> currentEvents;

//    @JsonIgnore
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "user_meetings",
//            joinColumns = { @JoinColumn(name = "user_id")},
//            inverseJoinColumns = { @JoinColumn(name = "meeting_id")}
//    )
//    private List<Meeting> currentMeetings;

//    @JsonIgnore
//    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            mappedBy = "recipient"
//    )
//    private List<Invitation> pendingInvitations;

}
