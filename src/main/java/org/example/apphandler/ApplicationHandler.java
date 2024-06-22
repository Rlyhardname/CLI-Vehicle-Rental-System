package org.example.apphandler;

import org.example.configurations.DataLiveConfig;
import org.example.exceptions.RestartApplicationException;
import org.example.models.Customer;
import org.example.models.DateConfig;
import org.example.businesslogic.invoice.Invoice;
import org.example.models.Vehicle;
import org.example.models.enums.VehicleTypes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

import static org.example.models.enums.VehicleTypes.*;

/***
 * Go to Line 155 for to change invoice dates...
 */

public class ApplicationHandler {
    private String currentCustomer;
    private Scanner sc;
    private DataLiveConfig dataLiveConfig;


    public static void start(String... args) {
        new ApplicationHandler(args);
    }


    private ApplicationHandler(String... args) {
        sc = new Scanner(System.in);
        dataLiveConfig = new DataLiveConfig(new HashMap<>(), new HashMap<>(), new HashMap<>());
        dataLiveConfig.initAvailableVehicles();
        dataLiveConfig.initRentedOutVehicles();
        dataLiveConfig.seedDummyCustomers();

        setCurrentCustomerDemo();

        selectionScreen();
    }

    private void setCurrentCustomerDemo() {
        while (true) {
            System.out.println("For the demo please choose a user from 1 to 3. 1 has a car, 2 has a motorcycle, 3 has a Cargo van");
            try {
                int customerDemo = Integer.parseInt(sc.nextLine());
                setCurrentCustomer(setCustomerDemo(customerDemo));
                break;
            } catch (NumberFormatException e) {

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String setCustomerDemo(int demoNumber) {
        switch (demoNumber) {
            case 1:
                return "Benjamin Bok";
            case 2:
                return "John Arrow";
            case 3:
                return "Kevin Heart";
            default:
                throw new IllegalArgumentException("Please choose from 1 to 3");
        }
    }

    private void setCurrentCustomer(String names) {
        currentCustomer = names;
    }

    private void selectionScreen() {
        while (true) {
            try {
                System.out.println("type 1 for rental service, 2 for return rental vehicle, 3 for print all vehicles");
                int operationId = Integer.parseInt(sc.nextLine());
                executeOperation(operationId);
            } catch (NumberFormatException e) {
                System.out.println("unsupported Operation, try again");
            } catch (RestartApplicationException e) {

            }

        }
    }

    private void executeOperation(int operationId) {
        switch (operationId) {
            case 1:
                rentService();
                break;
            case 2:
                invoice();
                break;
            case 3:
                printAvailableAndRentedOutVehicles();
                break;
        }
    }

    private void rentService() {
        while (true) {
            try {
                String vehicleType = chooseVehicleType();

                int indexOfVehicle = chooseVehicle();

                finalizeRent(vehicleType, indexOfVehicle);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You've chosen a vehicle outside the list, please use a list number from the available prepended numbers");
            }
        }
    }

    private void invoice() {
        while (true) {
            try {
                System.out.println("Type the id of the vehicle that you wish to return");
                int returnVehicleId = Integer.parseInt(sc.nextLine());
                Customer customer = dataLiveConfig.getCustomerMap().get(currentCustomer);
                System.out.println(customer);

                Vehicle vehicle = null;
                for (Map.Entry<String, List<Vehicle>> entry : dataLiveConfig.getRentedOutVehiclesMap().entrySet()) {
                    if (vehicle != null) {
                        break;
                    }

                    for (Vehicle currentVehicle : entry.getValue()) {
                        if (currentVehicle.getVehicleId() == returnVehicleId) {
                            vehicle = currentVehicle;
                            break;
                        }
                    }
                }

                if (vehicle == null) {
                    throw new IllegalArgumentException(String.format("Returned car with id of %d not rented our or doesn't belong to the business...", returnVehicleId));
                }

                if (!customer.tryRemoveVehicleFromRented(returnVehicleId)) {
                    throw new IllegalArgumentException(String.format("Customer has not rented out a vehicle with the id of %d", returnVehicleId));
                }

                returnVehicle(vehicle, customer);

                /***
                 *
                 * Hard coded invoice Dates, can be improved by customer storing dates himself and not
                 * only vehicle's rented.
                 */

//                Invoice invoice1 = new Invoice(vehicle,
//                        customer,
//                        new DateConfig(LocalDate.of(2024, 6, 3),
//                                LocalDate.of(2024, 6, 13),
//                                LocalDate.of(2024, 6, 13)));
//
//                Invoice invoice2 = new Invoice(vehicle,
//                        customer,
//                        new DateConfig(LocalDate.of(2024, 6, 3),
//                                LocalDate.of(2024, 6, 13),
//                                LocalDate.of(2024, 6, 13)));

                Invoice invoice = new Invoice(vehicle,
                        customer,
                        new DateConfig(LocalDate.of(2024, 6, 3),
                                LocalDate.of(2024, 6, 18),
                                LocalDate.of(2024, 6, 13)));

                printInvoice(invoice);


                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private String chooseVehicleType() {
        while (true) {
            System.out.println("What type of vehicle do you want to rent? %n Type 1 for car, type 2 for motorcycle, type 3 for Cargo van");
            try {
                int vehicleTypeId = Integer.parseInt(sc.nextLine());
                return displayVehicleInformation(vehicleTypeId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please write a number as suggested by the prompt...");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private String displayVehicleInformation(int vehicleId) {
        switch (vehicleId) {
            case 1 -> {
                vehicleInfo(CARS);
                return CARS.name();
            }
            case 2 -> {
                vehicleInfo(MOTORCYCLES);
                return MOTORCYCLES.name();
            }
            case 3 -> {
                vehicleInfo(CARGO_VANS);
                return CARGO_VANS.name();
            }

            default -> throw new IllegalArgumentException("Unsupported vehicle type");
        }
    }

    private void vehicleInfo(VehicleTypes vehicleTypes) {
        List<Vehicle> selectedVehicles = dataLiveConfig.getAvailableVehiclesMap().get(vehicleTypes.name());
        System.out.println("List of available vehicles of chosen category:");
        for (int i = 0; i < selectedVehicles.size(); i++) {
            System.out.printf(i + ":%s%n", selectedVehicles.get(i));
            System.out.flush();
        }

        System.out.println();
    }

    private int chooseVehicle() {
        do {
            System.out.println("Type the number prepended to the vehicle you're interested to rent");
            try {
                int vehicleId = Integer.parseInt(sc.nextLine());
                return vehicleId;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please write a number as suggested by the prompt...");
            }


        } while (true);
    }

    private void finalizeRent(String vehicleType, int indexOfVehicle) {
        System.out.println();
        do {
            System.out.printf("You've chosen vehicle %s%n%n" +
                    "Please type 'y' for confirmation or 'n' to go back" +
                    "to the selection screen" +
                    ",or 'end' to exit the application%n", dataLiveConfig.getAvailableVehiclesMap().get(vehicleType).get(indexOfVehicle));
            System.out.flush();
            String line = sc.nextLine();
            if (isRenting(line, vehicleType, indexOfVehicle)) {
                throw new RestartApplicationException();
            }
        } while (true);

    }

    private boolean isRenting(String line, String vehicleType, int indexOfVehicle) {
        switch (line.toLowerCase()) {
            case "y":
                Vehicle chosenRental = dataLiveConfig.getAvailableVehiclesMap().get(vehicleType).get(indexOfVehicle);
                printFinalRentStatement(vehicleType, indexOfVehicle);
                rentOutVehicle(chosenRental);
                return true;
            case "n":
                throw new RestartApplicationException();
            case "end":
                terminateApplication();
            default:
                System.out.println("Invalid input... %n%n");
                return false;
        }
    }

    private void rentOutVehicle(Vehicle chosenRental) {
        var customer = dataLiveConfig.getCustomerMap().get(currentCustomer);
        customer.addVehicleToRented(chosenRental.getVehicleId());
        dataLiveConfig.removeFromAvailableAddToRented(chosenRental);
    }

    private void returnVehicle(Vehicle returnedVehicle, Customer customer) {
        customer.tryRemoveVehicleFromRented(returnedVehicle.getVehicleId());
        dataLiveConfig.removeFromRentedAddToAvailable(returnedVehicle);
    }

    private void terminateApplication() {
        cleanUpResources();
        System.exit(0);
    }

    private void cleanUpResources() {
        sc.close();
    }

    private void printFinalRentStatement(String vehicleType, int rentalId) {
        System.out.println("Congratulations, you've chosen to rent out "+dataLiveConfig.getRentedOutVehiclesMap().get(vehicleType).get(rentalId));
    }

    private void printAvailableAndRentedOutVehicles() {
        printAvailableVehicles();
        printRentedOutVehicles();
        System.out.println();
    }

    private void printAvailableVehicles() {
        System.out.println("List of available vehicles:");
        printDataStructures(dataLiveConfig.getAvailableVehiclesMap());
    }

    private void printRentedOutVehicles() {
        System.out.println("List of rented out vehicles:");
        printDataStructures(dataLiveConfig.getRentedOutVehiclesMap());
    }

    private void printDataStructures(Map<String, List<Vehicle>> selectedMap) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int increment = 1;
        IntBinaryOperator intBinaryOperator = Integer::sum;
        for (Map.Entry<String, List<Vehicle>> vehicles : selectedMap.entrySet()) {
            vehicles.getValue().forEach(x -> System.out.printf(atomicInteger.getAndAccumulate(increment, intBinaryOperator) + ":%s%n", x));
            System.out.flush();
        }

    }

    private void printInvoice(Invoice invoice) {
        System.out.println(invoice);
    }
}
