import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.controller.VacationController;
import org.example.dto.VacationRequest;
import org.example.service.VacationService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@WebMvcTest(VacationController.class)
public class VacationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VacationService vacationService;

    @Test
    public void testCalculateWithVacationDays() throws Exception {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                10,
                null,
                null
        );

        when(vacationService.calculate(any(VacationRequest.class)))
                .thenReturn(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(10)));

        mockMvc.perform(post("/calculate")
                        .contentType("application/json")
                        .content("{\"averageSalary\":1000.00,\"vacationDays\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(10000.00));
    }

    @Test
    public void testCalculateWithStartAndEndDate() throws Exception {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                null,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10)
        );

        when(vacationService.calculate(any(VacationRequest.class)))
                .thenReturn(BigDecimal.valueOf(1000).divide(BigDecimal.valueOf(29.3), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(8)));

        mockMvc.perform(post("/calculate")
                        .contentType("application/json")
                        .content("{\"averageSalary\":1000.00,\"startDate\":\"2025-05-01\",\"endDate\":\"2025-05-10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(272.99));  // Примерный результат
    }

    @Test
    public void testInvalidVacationRequest() throws Exception {
        mockMvc.perform(post("/calculate")
                        .contentType("application/json")
                        .content("{\"averageSalary\":1000.00}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Specify either vacationDays or both startDate and endDate."));
    }
}