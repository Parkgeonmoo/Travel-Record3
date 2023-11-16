package toy.five.triprecord.domain.trip.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.dto.response.TripDetailResponse;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static toy.five.triprecord.domain.trip.entity.Domestic.DOMESTIC;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    private final Trip defaultTrip =
            Trip.builder()
                    .name("여행 조건")
                    .startTime(LocalDateTime.of(2022,1,1,0,0,0))
                    .endTime(LocalDateTime.of(2023,1,1,0,0,0))
                    .domestic(DOMESTIC)
                    .wishCount(10L)
                    .build();

    @Test
    void getAllTripsBySearch_allConditions() {
        //given
        TripSearchCond cond =
                TripSearchCond.builder()
                        .tripName("조건")
                        .minStartTime(LocalDateTime.of(2022,01,01,0,0,0))
                        .maxEndTime(LocalDateTime.of(2023,01,01,0,0,0))
                        .domestic(DOMESTIC)
                        .minWishCount(10L)
                        .build();

        given(tripRepository.findAllBySearchCond(any(TripSearchCond.class)))
                .willReturn(Collections.singletonList(defaultTrip));

        //when
        List<TripDetailResponse> tripDetailList =
                tripService.getAllTripsBySearchCond(cond);

        //then
        assertTrue(tripDetailList.get(0).getName().contains(cond.getTripName()));
        assertTrue(
                cond.getMinStartTime().isBefore(tripDetailList.get(0).getStartTime()) ||
                        cond.getMinStartTime().isEqual(tripDetailList.get(0).getStartTime())
        );
        assertTrue(
                cond.getMaxEndTime().isAfter(tripDetailList.get(0).getEndTime()) ||
                        cond.getMaxEndTime().isEqual(tripDetailList.get(0).getEndTime())
        );
        Assertions.assertThat(cond.getDomestic()).isEqualTo(tripDetailList.get(0).getDomestic());
        assertTrue(cond.getMinWishCount() <= tripDetailList.get(0).getWishCount());

    }


}