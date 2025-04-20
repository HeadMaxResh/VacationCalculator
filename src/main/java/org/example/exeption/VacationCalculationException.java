package org.example.exeption;

public class VacationCalculationException extends RuntimeException {
    public VacationCalculationException(String message) {
        super(message);
    }
}
