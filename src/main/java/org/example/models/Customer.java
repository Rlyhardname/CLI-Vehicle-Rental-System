package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private int yearsOfDrivingExperience;
    // this list will change to map<VehicleId,DateConfig>
    private List<Integer> rentedOutVehicleIdsList;

    public Customer(String firstName, String lastName, int age, int yearsOfDrivingExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.yearsOfDrivingExperience = yearsOfDrivingExperience;
        this.rentedOutVehicleIdsList = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public int getYearsOfDrivingExperience() {
        return yearsOfDrivingExperience;
    }

    public List<Integer> getRentedOutVehicleIdsList() {
        return rentedOutVehicleIdsList;
    }

    public String nameCombination() {
        return firstName + " " + lastName;
    }

    public void addVehicleToRented(int id) {
        rentedOutVehicleIdsList.add(id);
    }

    public boolean tryRemoveVehicleFromRented(int id) {
        for (int i = 0; i < rentedOutVehicleIdsList.size(); i++) {
            if (rentedOutVehicleIdsList.get(i) == id) {
                rentedOutVehicleIdsList.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", yearsOfDrivingExperience=" + yearsOfDrivingExperience +
                ", rentedOutVehicleIdsList=" + rentedOutVehicleIdsList +
                '}';
    }
}
