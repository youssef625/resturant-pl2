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

                case 2:
                    flush();
                    employeePortal employeePortal = new employeePortal();
                    break;


                case 3:
                    flush();
                    customerPortal customerPortal = new customerPortal();
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
