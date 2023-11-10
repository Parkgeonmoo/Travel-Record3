package toy.five.triprecord.domain.jouney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;

import java.util.List;

public interface LodgmentJourneyRepository extends JpaRepository<LodgmentJourney, Long> {

    List<LodgmentJourney> findAllByTripId(Long tripId);
}
