package toy.five.triprecord.domain.trip.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import toy.five.triprecord.domain.trip.dto.request.TripSearchCond;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.entity.Trip;

import java.time.LocalDateTime;
import java.util.List;

import static toy.five.triprecord.domain.trip.entity.QTrip.trip;

@Repository //이거 없어도 되는지 확인 해보기
@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Trip> findAllBySearchCond(TripSearchCond cond) {
        return queryFactory.select(trip)
                .from(trip)
                .where(
                        likeTripName(cond.getTripName()),
                        greaterThanStartTime(cond.getMinStartTime()),
                        lessThanEndTime(cond.getMaxEndTime()),
                        isDomesticOrNot(cond.getDomestic()),
                        greaterThanWishCount(cond.getMinWishCount())
                )
                .fetch();
    }

    private BooleanExpression likeTripName(String tripName) {
        if(StringUtils.hasText(tripName)) {
            return trip.name.like("%" + tripName + "%");
        }
        return null;
    }

    private BooleanExpression greaterThanStartTime(LocalDateTime startTime) {
        if(startTime != null) {
            return trip.startTime.goe(startTime);
        }
        return null;
    }

    private BooleanExpression lessThanEndTime(LocalDateTime endTime) {
        if(endTime != null) {
            return trip.endTime.loe(endTime);
        }
        return null;
    }

    private BooleanExpression isDomesticOrNot(Domestic isDomestic) {
        if(isDomestic != null) {
            return trip.domestic.eq(isDomestic);
        }
        return null;
    }

    private BooleanExpression greaterThanWishCount(Long wishCount) {
        if(wishCount != null) {
            return trip.wishCount.goe(wishCount);
        }
        return null;
    }

}
