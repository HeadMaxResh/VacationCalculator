package org.example.validation;

import org.example.dto.VacationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VacationRequestValidator implements ConstraintValidator<ValidVacationRequest, VacationRequest> {

    private String message;

    @Override
    public void initialize(ValidVacationRequest constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(VacationRequest request, ConstraintValidatorContext context) {
        boolean hasRange = request.getStartDate() != null && request.getEndDate() != null;
        boolean hasDays = request.getVacationDays() != null;

        boolean isValid = hasDays ^ hasRange;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}