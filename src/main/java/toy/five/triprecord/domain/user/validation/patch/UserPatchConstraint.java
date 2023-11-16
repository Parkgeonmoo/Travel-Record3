package toy.five.triprecord.domain.user.validation.patch;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import toy.five.triprecord.domain.trip.validation.patch.TripPatchTimeConstraintValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserPatchConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPatchConstraint {
    String message() default "패스워드와 이름 중 하나는 입력되어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
