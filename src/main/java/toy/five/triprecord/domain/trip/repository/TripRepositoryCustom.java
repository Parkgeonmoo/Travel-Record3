package toy.five.triprecord.domain.trip.repository;

import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.entity.Trip;

import java.util.List;

public interface TripRepositoryCustom {
    List<Trip> findAllBySearchCond(TripSearchCond cond);
}
