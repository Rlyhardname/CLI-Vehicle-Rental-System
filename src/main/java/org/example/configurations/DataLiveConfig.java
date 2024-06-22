package org.example.configurations;

import org.example.models.*;

import java.util.*;

import static org.example.models.enums.VehicleTypes.*;

public class DataLiveConfig {
    private Map<String, List<Vehicle>> availableVehiclesMap;
    private Map<String, List<Vehicle>> rentedOutVehiclesMap;
    private Map<String, Customer> customerMap;

    public DataLiveConfig(Map<String, List<Vehicle>> availableVehiclesMap, Map<String, List<Vehicle>> rentedOutVehiclesMap, Map<String, Customer> customerMap) {
        this.availableVehiclesMap = availableVehiclesMap;
        this.rentedOutVehiclesMap = rentedOutVehiclesMap;
        this.customerMap = customerMap;
    }

    public void initAvailableVehicles() {

        availableVehiclesMap = new HashMap<>();
        availableVehiclesMap.put(CARS.name(), new ArrayList<>());
        availableVehiclesMap.put(MOTORCYCLES.name(), new ArrayList<>());
        availableVehiclesMap.put(CARGO_VANS.name(), new ArrayList<>());

        List<Vehicle> cars = availableVehiclesMap.get(CARS.name());
        cars.add(new Car("Mercedes", "EQE SUV", 4, 80000.00, 1));
        cars.add(new Car("Volvo", "240", 2, 5000.00, 2));
        cars.add(new Car("Bugatti", "Chiron super sport 300", 5, 5000000.00, 3));

        List<Vehicle> motorcycles = availableVehiclesMap.get(MOTORCYCLES.name());
        motorcycles.add(new Motorcycle("Harley Davidson", "V-ROD", 16500.00, 6));

        List<Vehicle> cargoVans = availableVehiclesMap.get(CARGO_VANS.name());
        cargoVans.add(new CargoVan("Ford Transit", "2024", 50000.00, 8));
    }

    public void initRentedOutVehicles() {
        rentedOutVehiclesMap = new HashMap<>();

        rentedOutVehiclesMap.put(CARS.name(), new ArrayList<>());
        rentedOutVehiclesMap.get(CARS.name()).add(new Car("Mitsubishi", "Mirage", 3, 15000.00, 4));

        rentedOutVehiclesMap.put(MOTORCYCLES.name(), new ArrayList<>());
        rentedOutVehiclesMap.get(MOTORCYCLES.name()).add(new Motorcycle("Triumph", "Tiger sport 660", 10000.00, 5));

        rentedOutVehiclesMap.put(CARGO_VANS.name(), new ArrayList<>());
        rentedOutVehiclesMap.get(CARGO_VANS.name()).add(new CargoVan("Citroen", "Jumper", 20000.00, 7));
    }

    public void seedDummyCustomers() {
        Customer customer1 = new Customer("Benjamin", "Bok", 33, 4);
        customer1.addVehicleToRented(4);
        getCustomerMap().put(customer1.nameCombination(), customer1);

        Customer customer2 = new Customer("John", "Arrow", 22, 4);
        customer2.addVehicleToRented(5);
        getCustomerMap().put(customer2.nameCombination(), customer2);

        Customer customer3 = new Customer("Kevin", "Heart", 48, 6);
        customer3.addVehicleToRented(7);
        getCustomerMap().put(customer3.nameCombination(), customer3);
    }

    public Map<String, List<Vehicle>> getAvailableVehiclesMap() {
        return availableVehiclesMap;
    }

    public Map<String, List<Vehicle>> getRentedOutVehiclesMap() {
        return rentedOutVehiclesMap;
    }

    public Map<String, Customer> getCustomerMap() {
        return customerMap;
    }

    public void removeFromRentedAddToAvailable(Vehicle vehicle) {
        Iterator<Vehicle> iter = rentedOutVehiclesMap.get(vehicle.vehicleType()).iterator();
        while (iter.hasNext()) {
            Vehicle currentVehicle = iter.next();
            if (currentVehicle.getVehicleId() == vehicle.getVehicleId()) {
                availableVehiclesMap.get(vehicle.vehicleType()).add(vehicle);
                iter.remove();
                break;
            }
        }

    }

    public void removeFromAvailableAddToRented(Vehicle vehicle) {
        Iterator<Vehicle> iter = availableVehiclesMap.get(vehicle.vehicleType()).iterator();
        while (iter.hasNext()) {
            Vehicle currentVehicle = iter.next();
            if (currentVehicle.getVehicleId() == vehicle.getVehicleId()) {
                rentedOutVehiclesMap.get(vehicle.vehicleType()).add(vehicle);
                iter.remove();
                break;
            }
        }

    }

}
