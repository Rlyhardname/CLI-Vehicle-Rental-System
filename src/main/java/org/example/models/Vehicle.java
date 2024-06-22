package org.example.models;

public abstract class Vehicle {
    private int vehicleId;
    private String brand;
    private String model;
    private double value;

    public Vehicle(String brand, String model, double value, int vehicleId) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.value = value;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getValue() {
        return value;
    }

    public abstract String vehicleType();

    public abstract double calculateInsuranceCostPerDay();

    public abstract double calculateRentalCostPerDay(boolean isMoreThanWeek);

    @Override
    public String toString() {
        return "vehicleType=" + vehicleType() +
                " vehicleId=" + vehicleId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", value=" + value;
    }
}
