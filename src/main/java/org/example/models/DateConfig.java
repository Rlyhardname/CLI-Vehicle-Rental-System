package org.example.models;

import java.time.LocalDate;

public class DateConfig {
    private final LocalDate reserveDate;
    private final LocalDate expectedReturnDate;
    private final LocalDate actualReturnDate;

    public DateConfig(LocalDate reserveDate, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        this.reserveDate = reserveDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }
}
