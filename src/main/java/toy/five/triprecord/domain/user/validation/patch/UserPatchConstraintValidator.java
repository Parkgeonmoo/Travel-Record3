package toy.five.triprecord.domain.user.validation.patch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import toy.five.triprecord.domain.user.dto.request.UserPatchRequest;
import toy.five.triprecord.global.exception.ErrorCode;
import toy.five.triprecord.global.exception.ValidationCode;

public class UserPatchConstraintValidator implements ConstraintValidator<UserPatchConstraint, UserPatchRequest> {

    @Override
    public void initialize(UserPatchConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserPatchRequest userPatchRequest, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(userPatchRequest.getPassword()) && StringUtils.isBlank(userPatchRequest.getName())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidationCode.USER_PATCH_NO_PARAMETER_ERROR.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }


}
