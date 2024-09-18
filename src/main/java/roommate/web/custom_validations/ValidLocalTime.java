package roommate.web.custom_validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalTimeValidator.class)
public @interface ValidLocalTime {
    String message() default "Invalid local time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}