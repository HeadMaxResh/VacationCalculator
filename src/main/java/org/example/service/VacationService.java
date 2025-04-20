package org.example.service;

import org.example.dto.VacationRequest;

import java.math.BigDecimal;

public interface VacationService {

    BigDecimal calculate(VacationRequest request);

}
