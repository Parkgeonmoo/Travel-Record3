package toy.five.triprecord.domain.jouney.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;

import java.util.List;

public interface MoveJourneyRepository extends JpaRepository<MoveJourney, Long> {
    List<MoveJourney> findAllByTripId(Long tripId);
}
