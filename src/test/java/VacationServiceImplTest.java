import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.dto.VacationRequest;
import org.example.exeption.VacationCalculationException;
import org.example.service.VacationServiceImpl;
import org.example.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
public class VacationServiceImplTest {

    @Mock
    private DateUtils dateUtils;

    @InjectMocks
    private VacationServiceImpl vacationService;

    @Test
    public void testCalculateWithVacationDays() {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                10,
                null,
                null
        );

        BigDecimal result = vacationService.calculate(request);
        assertEquals(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(10)), result);
    }

    @Test
    public void testCalculateWithStartAndEndDate() {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                null,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10)
        );

        when(dateUtils.getWorkingDaysBetween(any(), any())).thenReturn(8);

        BigDecimal result = vacationService.calculate(request);
        assertEquals(BigDecimal.valueOf(1000).divide(BigDecimal.valueOf(29.3), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(8)), result);
    }

    @Test
    public void testCalculateWithNoVacationDaysOrRange() {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                null,
                null,
                null
        );

        Exception exception = assertThrows(VacationCalculationException.class, () -> vacationService.calculate(request));
        assertEquals("You must specify either vacationDays or startDate and endDate.", exception.getMessage());
    }

    @Test
    public void testCalculateWithZeroWorkingDays() {
        VacationRequest request = new VacationRequest(
                BigDecimal.valueOf(1000),
                null,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 1)
        );

        when(dateUtils.getWorkingDaysBetween(any(), any())).thenReturn(0);

        Exception exception = assertThrows(VacationCalculationException.class, () -> vacationService.calculate(request));
        assertEquals("There are no working days in the selected range.", exception.getMessage());
    }
}