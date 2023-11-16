package toy.five.triprecord.domain.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.five.triprecord.domain.wish.entity.Wish;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByUserAndTrip(User user, Trip trip);
    boolean existsByUserAndTrip(User user, Trip trip);
}
