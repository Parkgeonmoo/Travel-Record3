package toy.five.triprecord.domain.jouney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.entity.VisitJourney;

import java.util.List;

public interface VisitJourneyRepository extends JpaRepository<VisitJourney, Long> {
    List<VisitJourney> findAllByTripId(Long tripId);
}
