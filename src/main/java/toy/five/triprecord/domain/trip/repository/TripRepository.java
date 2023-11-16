package toy.five.triprecord.domain.trip.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.user.entity.User;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    List<Trip> findAllByUser(User user, Pageable pageable);
}
