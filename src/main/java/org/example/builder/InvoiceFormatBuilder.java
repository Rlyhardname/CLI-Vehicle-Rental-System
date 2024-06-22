package org.example.builder;

public class InvoiceFormatBuilder {
    String result;

    public void base() {
        result += "%s" +
                "Date: %s%n" +
                "Customer Names: %s%n" +
                "RentedVehicle: %s %s%n%n" +
                "Reservation start date: %s%n" +
                "Reservation end date: %s%n" +
                "Reservation rental days: %d days%n%n" +
                "Actual return date: %s%n" +
                "Actual rental days: %d%n%n" +
                "Rental cost per day: $%.2f%n";
    }

    public void baseAdditionalNoPenaltiesOrDiscounts() {
        result += "Insurance per day: $%.2f%n%n";
    }

    public void underAgedDriver() {
        result += "Initial insurance per day: $%.2f%n" +
                "Insurance additional per day: $%.2f%n";
    }

    public void experienceDiscount() {
        result += "Initial insurance per day: $%.2f%n" +
                "Insurance discount per day: $%.2f%n";
    }

    public void returnEarlyDiscount() {
        result += "Early return discount for rent: $%s%n" +
                "Early return discount for insurance: $%s%n";
    }

    public void footer() {
        result += "Total rent: $%.2f%n" +
                "Total insurance: $%.2f%n" +
                "Total: $%.2f%n" +
                "%s";
    }

    public String build() {
        return result;
    }
}
