package hr.luka.b2match.data.repository;

import hr.luka.b2match.data.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findAllByStartTimeAfter(LocalDateTime endTime);
}
