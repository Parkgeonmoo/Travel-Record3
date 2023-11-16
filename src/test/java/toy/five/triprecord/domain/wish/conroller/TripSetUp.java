package toy.five.triprecord.domain.wish.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toy.five.triprecord.domain.trip.dto.request.TripCreateRequest;
import toy.five.triprecord.domain.trip.dto.response.TripCreateResponse;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.service.TripService;

import java.time.LocalDateTime;

import static toy.five.triprecord.domain.trip.entity.Domestic.DOMESTIC;

@Component
public class TripSetUp {

    @Autowired
    private TripService tripService;

    private final TripCreateRequest tripCreateRequest =
            TripCreateRequest.builder()
                    .name("여행!@#%@")
                    .domestic(DOMESTIC)
                    .startTime(LocalDateTime.of(2022, 1, 1, 0, 0))
                    .endTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                    .build();

    public TripCreateResponse saveTrip() {
        return tripService.createTrip(tripCreateRequest);
    }

}
