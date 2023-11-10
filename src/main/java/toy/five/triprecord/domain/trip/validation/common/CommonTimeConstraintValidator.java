package toy.five.triprecord.domain.trip.validation.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import toy.five.triprecord.global.util.BaseTimeRequest;

import static toy.five.triprecord.global.exception.ValidationCode.TRIP_START_TIME_ERROR;
import static toy.five.triprecord.global.exception.ValidationCode.TRIP_TIME_ERROR;

public class CommonTimeConstraintValidator implements ConstraintValidator<CommonTimeConstraint, BaseTimeRequest> {

    @Override
    public void initialize(CommonTimeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(BaseTimeRequest baseTimeRequest, ConstraintValidatorContext context) {
        if (baseTimeRequest.getStartTime() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(TRIP_START_TIME_ERROR.getMessage())
                    .addConstraintViolation();
            return false;
        }
        if (baseTimeRequest.getEndTime() != null && baseTimeRequest.getStartTime() != null
                && baseTimeRequest.getStartTime().isAfter(baseTimeRequest.getEndTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(TRIP_TIME_ERROR.getMessage())
                    .addPropertyNode("startTime")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }


}
