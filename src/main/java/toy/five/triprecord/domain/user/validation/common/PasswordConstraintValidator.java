package toy.five.triprecord.domain.user.validation.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import toy.five.triprecord.global.exception.ValidationCode;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint,String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // ^(?=.*[a-zA-Z])(?=.*\d).{6,10}$
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d).{6,10}$";

        if (!value.matches(pattern)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidationCode.USER_COMMNON_PASSWORD_MISMATCH_ERROR.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
