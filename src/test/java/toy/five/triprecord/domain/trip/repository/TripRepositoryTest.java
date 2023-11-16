package toy.five.triprecord.domain.trip.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.global.config.QueryDSLConfig;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static toy.five.triprecord.domain.trip.entity.Domestic.DOMESTIC;

@DataJpaTest
@Import({QueryDSLConfig.class})
class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void findAllBySearchCond_AllConditions() {
        //given
        TripSearchCond cond =
                TripSearchCond.builder()
                        .tripName("조건")
                        .minStartTime(LocalDateTime.of(2022,01,01,0,0,0))
                        .maxEndTime(LocalDateTime.of(2023,01,01,0,0,0))
                        .domestic(DOMESTIC)
                        .minWishCount(10L)
                        .build();

        //when
        List<Trip> findTrips = tripRepository.findAllBySearchCond(cond);

        //then
        for(Trip findTrip : findTrips) {
            assertTrue(findTrip.getName().contains(cond.getTripName()));
            assertTrue(
                    cond.getMinStartTime().isBefore(findTrip.getStartTime()) ||
                            cond.getMinStartTime().isEqual(findTrip.getStartTime())
            );
            assertTrue(
                    cond.getMaxEndTime().isAfter(findTrip.getEndTime()) ||
                            cond.getMaxEndTime().isEqual(findTrip.getEndTime())
            );
            Assertions.assertThat(cond.getDomestic()).isEqualTo(findTrip.getDomestic());
            assertTrue(cond.getMinWishCount() <= findTrip.getWishCount());

        }
    }



}