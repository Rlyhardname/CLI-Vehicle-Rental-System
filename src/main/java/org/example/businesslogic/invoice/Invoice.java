package org.example.businesslogic.invoice;

import org.example.models.*;
import org.example.models.enums.VehicleTypes;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.time.temporal.ChronoUnit.DAYS;

public class Invoice {
    private static final double earlyFeePerDayPercent = 0.5;
    private static final double lateFeePerDayPercent = 1.33;
    private static final double insuranceFee = 1.5;
    private final Vehicle vehicle;
    private final Customer customer;
    private final DateConfig dateConfig;
    private double finalRentalCost;
    private double finalInsuranceCost;


    public Invoice(Vehicle vehicle, Customer customer, DateConfig dateConfig) {
        this.dateConfig = dateConfig;
        this.vehicle = vehicle;
        this.customer = customer;
        calculateInsuranceCost();
        calculateRentalCost();

        if (isReturnedEarly()) {
            earlyFee();
        } else if (isReturnedLate()) {
            lateFee();
        }

    }

    private void calculateRentalCost() {
        finalRentalCost = roundToSecondDigit(vehicle.calculateRentalCostPerDay(isRentedMoreThanWeek()) * reservedRentalDays());
    }

    private void calculateInsuranceCost() {
        if (vehicle.getClass() == Car.class) {
            finalInsuranceCost = roundToSecondDigit(vehicle.calculateInsuranceCostPerDay() * reservedRentalDays());
        } else if (vehicle.getClass() == Motorcycle.class) {
            finalInsuranceCost = roundToSecondDigit(customer.getAge() < 25 ? (vehicle.calculateInsuranceCostPerDay() * 1.2) * reservedRentalDays() : vehicle.calculateInsuranceCostPerDay() * reservedRentalDays());
        } else if (vehicle.getClass() == CargoVan.class) {
            finalInsuranceCost = roundToSecondDigit(customer.getYearsOfDrivingExperience() > 5 ? (vehicle.calculateInsuranceCostPerDay() * 0.85) * reservedRentalDays() : vehicle.calculateInsuranceCostPerDay() * reservedRentalDays());
        }

    }

    protected boolean isReturnedEarly() {
        return reservedRentalDays() > actualRentalDays();
    }

    private void earlyFee() {
        long penaltyDays = reservedRentalDays() - actualRentalDays();
        long normalDays = reservedRentalDays() - penaltyDays;

        finalRentalCost = roundToSecondDigit(((vehicle.calculateRentalCostPerDay(isRentedMoreThanWeek()) * normalDays) + earlyRentalFeePerDay(penaltyDays)));

        finalInsuranceCost = roundToSecondDigit((vehicle.calculateInsuranceCostPerDay() * normalDays));
    }

    private double earlyRentalFeePerDay(long penaltyDays) {
        double result = ((vehicle.calculateRentalCostPerDay(isRentedMoreThanWeek()) * earlyFeePerDayPercent) * penaltyDays);
        return roundToSecondDigit(result);
    }

    private boolean isReturnedLate() {
        return reservedRentalDays() < actualRentalDays();
    }

    private void lateFee() {
        long daysOverdue = actualRentalDays() - reservedRentalDays();

        finalRentalCost += (vehicle.calculateRentalCostPerDay(InvoiceUtils.isRentPeriodMoreThanWeek(dateConfig.getReserveDate(), dateConfig.getExpectedReturnDate())) * lateFeePerDayPercent) * daysOverdue;

        finalInsuranceCost += (vehicle.calculateInsuranceCostPerDay() * insuranceFee) * daysOverdue;
    }

    public long reservedRentalDays() {
        return DAYS.between(dateConfig.getReserveDate(), dateConfig.getExpectedReturnDate());
    }

    public long actualRentalDays() {
        return DAYS.between(dateConfig.getReserveDate(), dateConfig.getActualReturnDate());
    }

    public long remainingDays() {
        return DAYS.between(dateConfig.getActualReturnDate(), dateConfig.getExpectedReturnDate());
    }

    protected double additionalInsuranceFeeMotorcyclePerDay() {
        double result = customer.getAge() < 25 ? vehicle.calculateInsuranceCostPerDay() * 0.2 : 0;
        return roundToSecondDigit(result);
    }

    protected double insuranceDiscountForExperiencePerDay() {
        double result = customer.getYearsOfDrivingExperience() > 5 ? vehicle.calculateInsuranceCostPerDay() * 0.15 : 0;
        return roundToSecondDigit(result);
    }

    protected double baseInsuranceCostWithExperiencePerDay() {
        double result = customer.getYearsOfDrivingExperience() > 5 ? vehicle.calculateInsuranceCostPerDay() * 0.85 : 1;
        return roundToSecondDigit(result);
    }

    protected double earlyReturnRateDiscount() {
        long remainingDays = remainingDays();
        double result = remainingDays * (vehicle.calculateRentalCostPerDay(isRentedMoreThanWeek()) * earlyFeePerDayPercent);
        return roundToSecondDigit(result);
    }

    protected double earlyInsuranceDiscount() {
        long remainingDays = remainingDays();
        double result = remainingDays * baseInsuranceCostWithExperiencePerDay();
        return roundToSecondDigit(result);
    }

    protected boolean isRentedMoreThanWeek() {
        return InvoiceUtils.isRentPeriodMoreThanWeek(dateConfig.getReserveDate(), dateConfig.getExpectedReturnDate());
    }

    private double roundToSecondDigit(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        if (vehicle.vehicleType().equals(VehicleTypes.CARS.name())) {
            return InvoiceUtils.buildCarInvoiceString(this);
        } else if (vehicle.vehicleType().equals(VehicleTypes.MOTORCYCLES.name())) {
            return InvoiceUtils.buildMotorcycleInvoiceString(this);
        } else if (vehicle.vehicleType().equals(VehicleTypes.CARGO_VANS.name())) {
            return InvoiceUtils.buildCargoVanInvoiceString(this);
        }

        return "";
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public DateConfig getDateConfig() {
        return dateConfig;
    }

    public double getFinalRentalCost() {
        return finalRentalCost;
    }

    public double getFinalInsuranceCost() {
        return finalInsuranceCost;
    }
}
