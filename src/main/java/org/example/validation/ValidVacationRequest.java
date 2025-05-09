package org.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VacationRequestValidator.class)
public @interface ValidVacationRequest {
    String message() default "Specify either vacationDays or both startDate and endDate.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
