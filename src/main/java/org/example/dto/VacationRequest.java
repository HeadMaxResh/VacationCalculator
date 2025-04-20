package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.validation.ValidVacationRequest;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@ValidVacationRequest
public class VacationRequest {

    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.01", message = "Salary must be at least 0.01")
    private BigDecimal averageSalary;

    @Min(value = 1, message = "Vacation days must be at least 1")
    private Integer vacationDays;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

}
