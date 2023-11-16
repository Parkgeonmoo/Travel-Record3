package toy.five.triprecord.domain.journey.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.repository.MoveJourneyRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({JourneyTestConfig.class})
public class JourneyRepositoryTest {

    @Autowired
    private MoveJourneyRepository moveJourneyRepository;

    @Autowired
    private TripRepository tripRepository;

    @Test
    void findAllByTripId() {
        // given
        Long tripId = 1L;
        Trip trip = Trip.builder()
                .id(tripId)
                .build();

        tripRepository.save(trip);

        MoveJourney moveJourney1 = MoveJourney.builder()
                .trip(trip)
                .name("이동01")
                .build();
        MoveJourney moveJourney2 = MoveJourney.builder()
                .trip(trip)
                .name("이동02")
                .build();

        moveJourneyRepository.save(moveJourney1);
        moveJourneyRepository.save(moveJourney2);

        // when
        List<MoveJourney> result = moveJourneyRepository.findAllByTripId(tripId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(moveJourney1, moveJourney2);
    }

}
