package toy.five.triprecord.domain.user.validation.patch;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import toy.five.triprecord.domain.user.validation.common.PasswordConstraintValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserPatchPasswordConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPatchPasswordConstraint {
    String message() default "패스워드는 대소문자 1개와 숫자를 포함한 6~10자리로 써주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
