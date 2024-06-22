package org.example.models;

import org.example.models.enums.VehicleTypes;

public class Motorcycle extends Vehicle {
    public Motorcycle(String brand, String model, double value, int vehicleId) {
        super(brand, model, value, vehicleId);
    }

    @Override
    public String vehicleType() {
        return VehicleTypes.MOTORCYCLES.name();
    }

    @Override
    public double calculateInsuranceCostPerDay() {
        return super.getValue()*0.0002;
    }

    @Override
    public double calculateRentalCostPerDay(boolean isMoreThanWeek) {
        if (isMoreThanWeek) {
            return 10.00;
        }

        return 15.00;
    }
}
