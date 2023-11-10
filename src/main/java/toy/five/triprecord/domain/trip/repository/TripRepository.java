package toy.five.triprecord.domain.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.trip.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
