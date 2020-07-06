package hr.luka.b2match.data.repository;

import hr.luka.b2match.data.model.Event;
import hr.luka.b2match.data.model.Meeting;
import hr.luka.b2match.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByEvent(Event event);

    List<Meeting> findAllByParticipants(User user);

}
