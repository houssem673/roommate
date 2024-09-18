package roommate.web.custom_validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

class LocalTimeValidator implements ConstraintValidator<ValidLocalTime, LocalTime> {
    @Override
    public void initialize(ValidLocalTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        if (value == null) {
            // Null values are considered invalid
            return false;
        }

        // Get the current date in Germany timezone
        LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Berlin"));

        // Check if the requested date is within the allowed range
        LocalDate requestedDate = LocalDate.now().plusYears(1); // One year ahead from today

        // Check if the requested time is not in the past and not more than a year ahead
        return !requestedDate.isBefore(currentDate) && !requestedDate.isAfter(currentDate.plusYears(1));
    }
}