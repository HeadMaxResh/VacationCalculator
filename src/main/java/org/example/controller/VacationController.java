package org.example.controller;

import org.example.dto.VacationRequest;
import org.example.dto.VacationResponse;
import org.example.service.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class VacationController {

    @Autowired
    private VacationServiceImpl vacationService;

    @PostMapping("/calculate")
    public ResponseEntity<VacationResponse> calculate(@Valid @RequestBody VacationRequest request) {
        var vacationPay = vacationService.calculate(request);
        VacationResponse response = new VacationResponse();
        response.setVacationPay(vacationPay);
        return ResponseEntity.ok(response);
    }

}
