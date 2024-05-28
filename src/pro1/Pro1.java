/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro1;


import java.time.LocalDateTime;
import java.util.Scanner;
abstract class Vehicle {
    private String make;
    private String model;
    private int registrationNumber;
    private double pricePerHour;
    private boolean isRented;
    private RentalDetail[] rentalDetails;
    private int rentalDetailsCount;

    public Vehicle(String make, String model, int registrationNumber, double pricePerHour) {
        this.make = make;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.pricePerHour = pricePerHour;
        this.isRented = false;
        this.rentalDetails = new RentalDetail[100]; 
        this.rentalDetailsCount = 0;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public boolean isRented() {
        return isRented;
    }

    public RentalDetail[] getRentalDetails() {
        return rentalDetails;
    }

    public int getRentalDetailsCount() {
        return rentalDetailsCount;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public void addRentalDetail(int carChoice, LocalDateTime rentalTime) {
        if (rentalDetailsCount < rentalDetails.length) {
            rentalDetails[rentalDetailsCount++] = new RentalDetail(carChoice, rentalTime);
        }
    }

    public abstract void displayDetails();
}

class RentalDetail {
    private int carChoice;
    private LocalDateTime rentalTime;

    public RentalDetail(int carChoice, LocalDateTime rentalTime) {
        this.carChoice = carChoice;
        this.rentalTime = rentalTime;
    }

    public int getCarChoice() {
        return carChoice;
    }

    public LocalDateTime getRentalTime() {
        return rentalTime;
    }
}

class Car extends Vehicle {
    public Car(String make, String model, int registrationNumber, double pricePerHour) {
        super(make, model, registrationNumber, pricePerHour);
    }

    @Override
    public void displayDetails() {
        System.out.println("Make: " + getMake() + ", Model: " + getModel() + ", Registration Number: " + getRegistrationNumber() +
                ", Price per Hour: $" + getPricePerHour() + ", Is Rented: " + isRented());
    }
}

public class Pro1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Vehicle[] vehicles = new Vehicle[5];
        double[] revenueList = new double[100]; // Fixed size array
        int revenueCount = 0;

        vehicles[0] = new Car("Toyota", "Camry", 1, 20.0);
        vehicles[1] = new Car("Honda", "Civic", 2, 25.0);
        vehicles[2] = new Car("Ford", "Fiesta", 3, 15.0);
        vehicles[3] = new Car("Chevrolet", "Malibu", 4, 22.0);
        vehicles[4] = new Car("BMW", "X5", 5, 40.0);

        while (true) {
            System.out.println("____________________________________________");
            System.out.println("*_*_*_*_*_*Car Rental System*_*_*_*_*_*");
            
            System.out.println("____________________________________________");
            System.out.println("1. Display All Cars");
            System.out.println("2. Display Available Cars");
            System.out.println("3. Rent a Car");
            System.out.println("4. Return a Car");
            System.out.println("5. View Revenue");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
               System.out.println("__________________________________");

            switch (choice) {
                case 1:
                    System.out.println("All Cars:");
                    for (Vehicle vehicle : vehicles) {
                        vehicle.displayDetails();
                    }
                    break;
                case 2:
                    System.out.println("Available Cars:");
                    for (Vehicle vehicle : vehicles) {
                        if (!vehicle.isRented()) {
                            System.out.println("Registration Number: " + vehicle.getRegistrationNumber());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Available Cars for Rent:");
                    int index = 1;
                    Vehicle[] availableCars = new Vehicle[vehicles.length];
                    int availableCarsCount = 0;
                    for (Vehicle vehicle : vehicles) {
                        if (!vehicle.isRented()) {
                            System.out.println(index + ". " + vehicle.getMake() + " " + vehicle.getModel() + " (" + vehicle.getRegistrationNumber() + ")");
                            availableCars[availableCarsCount++] = vehicle;
                            index++;
                        }
                    }
                    if (availableCarsCount > 0) {
                        System.out.print("Choose a car to rent by number: ");
                        int carChoice = scanner.nextInt();
                        if (carChoice > 0 && carChoice <= availableCarsCount) {
                            System.out.println("Do you want a driver? (yes/no)");
                            String driverChoice = scanner.next();
                            boolean needDriver = driverChoice.equalsIgnoreCase("yes");
                            double price = availableCars[carChoice - 1].getPricePerHour();
                            if (needDriver) {
                                price += 10.0; // Additional charge for driver
                            }
                            System.out.print("Enter rental duration in hours: ");
                            int hours = scanner.nextInt();
                            double totalPrice = price * hours;
                            System.out.println("Total Price: $" + totalPrice);
                            if (revenueCount < revenueList.length) {
                                revenueList[revenueCount++] = totalPrice;
                            }
                            availableCars[carChoice - 1].setRented(true);
                            availableCars[carChoice - 1].addRentalDetail(carChoice, LocalDateTime.now());
                            System.out.println("Car rented successfully!");
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    } else {
                        System.out.println("No cars available for rent.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Car Registration Number: ");
                    int regNumberReturn = scanner.nextInt();
                    boolean carFoundReturn = false;
                    for (Vehicle vehicle : vehicles) {
                        if (vehicle.getRegistrationNumber() == regNumberReturn) {
                            if (vehicle.isRented()) {
                                vehicle.setRented(false);
                                System.out.println("Car returned successfully!");
                            } else {
                                System.out.println("Car was not rented.");
                            }
                            carFoundReturn = true;
                            break;
                        }
                    }
                    if (!carFoundReturn) {
                        System.out.println("Car not found.");
                    }
                    break;
                case 5:
                    double totalRevenue = 0;
                    for (int i = 0; i < revenueCount; i++) {
                        totalRevenue += revenueList[i];
                    }
                    System.out.println("Total Revenue: $" + totalRevenue);

                    System.out.println("Today's Rental Details:");
                    LocalDateTime today = LocalDateTime.now();
                    for (Vehicle vehicle : vehicles) {
                        RentalDetail[] rentalDetails = vehicle.getRentalDetails();
                        for (int i = 0; i < vehicle.getRentalDetailsCount(); i++) {
                            RentalDetail rentalDetail = rentalDetails[i];
                            LocalDateTime rentalTime = rentalDetail.getRentalTime();
                            if (rentalTime.getDayOfYear() == today.getDayOfYear()) {
                                System.out.println("Car: " + vehicle.getMake() + " " + vehicle.getModel() +
                                        " (Registration Number: " + vehicle.getRegistrationNumber() + "), Rental Time: " + rentalTime);
                            }
                        }
                    }
                    break;
                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
