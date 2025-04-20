package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VacationResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal vacationPay;

}
