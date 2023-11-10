package toy.five.triprecord.domain.trip.validation.patch;

import org.springframework.stereotype.Component;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

import java.time.LocalDateTime;

@Component
public class TripPatchTimeValidatorUtils {

    public void startTimeCheckFromPatchRequest(TripPatchRequest tripPatchRequest, LocalDateTime nowTime) {
        if (tripPatchRequest.getStartTime() != null && tripPatchRequest.getStartTime().isAfter(nowTime)) {
            throw new BaseException(ErrorCode.TRIP_INVALID_TIME);
        }

    }
    public void endTimeCheckFromPatchRequest(TripPatchRequest tripPatchRequest, LocalDateTime nowTime) {
        if (tripPatchRequest.getEndTime() != null && nowTime.isAfter(tripPatchRequest.getEndTime())) {
            throw new BaseException(ErrorCode.TRIP_INVALID_TIME);
        }
    }


}
