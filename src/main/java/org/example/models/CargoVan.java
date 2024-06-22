package org.example.models;

import org.example.models.enums.VehicleTypes;

public class CargoVan extends Vehicle {
    public CargoVan(String brand, String model, double value, int vehicleId) {
        super(brand, model, value, vehicleId);
    }

    @Override
    public String vehicleType() {
        return VehicleTypes.CARGO_VANS.name();
    }

    @Override
    public double calculateInsuranceCostPerDay() {
        return super.getValue()*0.0003;
    }

    @Override
    public double calculateRentalCostPerDay(boolean isMoreThanWeek) {
        if (isMoreThanWeek) {
            return 40.00;
        }

        return 50.00;
    }
}
