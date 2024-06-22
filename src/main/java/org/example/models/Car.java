package org.example.models;

import org.example.models.enums.CarSafetyLevel;
import org.example.models.enums.VehicleTypes;

import java.util.Objects;

public class Car extends Vehicle {
    private int safetyRating;

    public Car(String brand, String model, int safetyRating, double value, int vehicleId) {
        super(brand, model, value, vehicleId);
        this.safetyRating = safetyRating;
    }

    private double calculateInsurancePerDayBasedOnSafety() {
        double insurance = super.getValue() * 0.0001;
        if (Objects.equals(carSafetyLevel(), CarSafetyLevel.HIGH_SAFETY.name())) {
            insurance -= insurance * 0.1;
        }

        return insurance;
    }

    private String carSafetyLevel() {
        if (getSafetyRating() > 3) {
            return CarSafetyLevel.HIGH_SAFETY.name();
        }

        if (getSafetyRating() == 3) {
            return CarSafetyLevel.MEDIUM_SAFETY.name();
        }

        if (getSafetyRating() < 3) {
            return CarSafetyLevel.LOW_SAFETY.name();
        }

        throw new IllegalArgumentException("Invalid rating input..");
    }

    @Override
    public String vehicleType() {
        return VehicleTypes.CARS.name();
    }

    @Override
    public double calculateInsuranceCostPerDay() {
        return calculateInsurancePerDayBasedOnSafety();
    }

    @Override
    public double calculateRentalCostPerDay(boolean isMoreThanWeek) {
        if (isMoreThanWeek) {
            return 15.00;
        }

        return 20.00;
    }

    public int getSafetyRating() {
        return safetyRating;
    }
}
