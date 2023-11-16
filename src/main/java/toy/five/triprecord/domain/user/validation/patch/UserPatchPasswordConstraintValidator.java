package toy.five.triprecord.domain.user.validation.patch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import toy.five.triprecord.domain.user.validation.common.PasswordConstraint;
import toy.five.triprecord.global.exception.ValidationCode;

public class UserPatchPasswordConstraintValidator implements ConstraintValidator<UserPatchPasswordConstraint,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d).{6,10}$";

        if (value == null) {
            return true;  // password is optional
        }

        if (!value.matches(pattern)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidationCode.USER_COMMNON_PASSWORD_MISMATCH_ERROR.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(UserPatchPasswordConstraint constraintAnnotation) {


    }
}
