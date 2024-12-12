package org.example;

import classes.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    // Method to clear the console screen
    public static void flush() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println("Checking database connection:");

        try {
            // Attempt to connect to the database
            Connection connection = db.connect();
            System.out.println("Database connection successful");
            flush();

        } catch (Exception e) {
            // Print an error message if the connection fails
            System.out.println("Error: " + e);
            return;
        }

        Scanner scanner = new Scanner(System.in); // Create a Scanner instance for user input
        Employee employee = null; // Define Employee object outside the loop

        while (true) {
            // Display the main menu
            System.out.println("Choose your role:");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");
            int role;

            try {
                // Read the user's role selection
                role = scanner.nextInt();
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("Invalid choice");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            switch (role) {
                case 1:
                    flush();
                    adminPortal admin = new adminPortal();
                    // Add admin functionality here if needed
                    break;

                case 2: // Employee role
                    flush();
                    if (employee == null) { // Check if Employee is already created
                        employee = new Employee();
                    }
                    while (true) {
                        // Display the employee options
                        System.out.println("Employee Options:");
                        System.out.println("1. Add Customer");
                        System.out.println("2. Delete Customer");
                        System.out.println("3. Update Customer");
                        System.out.println("4. List Customers");
                        System.out.println("5. Search Customers");
                        System.out.println("6. Record Payment");
                        System.out.println("7. Check Offers");
                        System.out.println("8. Back to Main Menu");
                        int empChoice;

                        try {
                            // Read the employee's choice
                            empChoice = scanner.nextInt();
                            scanner.nextLine(); // Clear the input buffer
                        } catch (Exception e) {
                            System.out.println("Invalid choice");
                            scanner.nextLine(); // Clear invalid input
                            continue;
                        }

                        switch (empChoice) {
                            case 1:
                                // Add functionality to add a customer
                                System.out.println("Adding a customer...");
                                break;
                            case 2:
                                // Add functionality to delete a customer
                                System.out.println("Deleting a customer...");
                                break;
                            case 3:
                                // Add functionality to update a customer
                                System.out.println("Updating a customer...");
                                break;
                            case 4:
                                // List customers
                                System.out.println("Listing customers...");
                                break;
                            case 5:
                                // Search customers
                                System.out.println("Searching for customers...");
                                break;
                            case 6:
                                // Record a payment
                                System.out.println("Recording payment...");
                                break;
                            case 7:
                                // Check offers
                                employee.checkOffers();
                                break;
                            case 8:
                                // Go back to the main menu
                                System.out.println("Returning to the main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }

                        if (empChoice == 8) {
                            break;
                        }
                    }
                    break;

                case 3:
                    flush();
                    System.out.println("Customer role not implemented yet.");
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
