package toy.five.triprecord.domain.wish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.wish.entity.Wish;
import toy.five.triprecord.domain.wish.repository.WishRepository;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.exception.BaseException;

import static toy.five.triprecord.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Transactional
    public long saveWish(Long userId, Long tripId) {
        User findUser = findUserById(userId);
        Trip findTrip = findTripById(tripId);

        if(isExistWishByUserAndTrip(findUser, findTrip))
            throw new BaseException(ALREADY_WISH);

        Wish wish = Wish.builder()
                .user(findUser)
                .trip(findTrip)
                .build();

        wishRepository.save(wish);
        return findTrip.plusWishCount();
    }

    @Transactional
    public long deleteWish(Long userId, Long tripId) {
        User findUser = findUserById(userId);
        Trip findTrip = findTripById(tripId);

        Wish findWish = wishRepository.findByUserAndTrip(findUser, findTrip)
                .orElseThrow(() -> new BaseException(LIKE_NO_EXIST));

        wishRepository.delete(findWish);
        return findTrip.minusWishCount();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NO_EXIST));
    }

    private Trip findTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new BaseException(TRIP_NO_EXIST));
    }

    private boolean isExistWishByUserAndTrip(User user, Trip trip) {
        return wishRepository.existsByUserAndTrip(user, trip);
    }
}
