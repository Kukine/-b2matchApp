package hr.luka.b2match.data.repository;

import hr.luka.b2match.data.model.Invitation;
import hr.luka.b2match.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByRecipient(User user);
    List<Invitation> findAllBySender(User user);
}
