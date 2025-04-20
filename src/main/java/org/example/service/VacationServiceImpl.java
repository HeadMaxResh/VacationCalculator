package org.example.service;

import org.example.dto.VacationRequest;
import org.example.dto.VacationResponse;
import org.example.exeption.VacationCalculationException;
import org.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class VacationService {

    private static final BigDecimal WORKING_DAYS_PER_MONTH = BigDecimal.valueOf(29.3);

    @Autowired
    private DateUtils dateUtils;

    public BigDecimal calculate(VacationRequest request) {
        BigDecimal dailyRate = request.getAverageSalary().divide(WORKING_DAYS_PER_MONTH, 2, RoundingMode.HALF_UP);

        if (request.getStartDate() != null && request.getEndDate() != null) {
            int workingDays = dateUtils.getWorkingDaysBetween(request.getStartDate(), request.getEndDate());

            if (workingDays <= 0 ) {
                throw new VacationCalculationException("There are no working days in the selected range.");
            }

            return dailyRate.multiply(BigDecimal.valueOf(workingDays)).setScale(2, RoundingMode.HALF_UP);
        } else if (request.getVacationDays() != null) {
            return dailyRate.multiply(BigDecimal.valueOf(request.getVacationDays())).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new VacationCalculationException("You must specify either vacationDays or startDate and endDate.");
        }
    }
}
