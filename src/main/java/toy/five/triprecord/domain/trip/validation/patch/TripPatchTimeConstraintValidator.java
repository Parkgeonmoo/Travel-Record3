package toy.five.triprecord.domain.trip.validation.patch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.global.exception.ErrorCode;


import static toy.five.triprecord.global.exception.ValidationCode.TRIP_TIME_ERROR;

public class TripPatchTimeConstraintValidator implements ConstraintValidator<TripPatchTimeConstraint, TripPatchRequest> {
    @Override
    public boolean isValid(TripPatchRequest patchRequest, ConstraintValidatorContext context) {
       /**if (isAllFieldsEmpty(patchRequest)) {
           throw new BaseException(ErrorCode.TRIP_IVALID_UPDATE);

       }**/

        if (isAllFieldsEmpty(patchRequest)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.TRIP_IVALID_UPDATE.getMessage())
                    .addConstraintViolation();
            return false;
        }

       if (patchRequest.getStartTime() != null && patchRequest.getEndTime() != null
                && patchRequest.getStartTime().isAfter(patchRequest.getEndTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(TRIP_TIME_ERROR.getMessage())
                    .addPropertyNode("startTime").addConstraintViolation()
            ;

            return false;
        }
        return true;
    }

    private boolean isAllFieldsEmpty(TripPatchRequest patchRequest) {
        return (StringUtils.isBlank(patchRequest.getName())) &&
                patchRequest.getStartTime() == null &&
                patchRequest.getEndTime() == null &&
                patchRequest.getDomestic() == null;

    }




}

