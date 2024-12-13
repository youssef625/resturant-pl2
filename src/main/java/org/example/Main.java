package org.example;

import classes.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Clear console
    public static void flush() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println("Checking database connection:");
        try {
            Connection connection = db.connect();
            System.out.println("Database connection successful");
            flush();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Employee employee = null;

        while (true) {
            System.out.println("Choose your role:");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");

            int role = getUserChoice(scanner);

            switch (role) {
                case 1:
                    flush();
                    adminPortal admin = new adminPortal();
                    System.out.println("Admin portal coming soon...");
                    break;

                case 2:
                    flush();
                    if (employee == null) {
                        employee = new Employee();
                    }
                    while (true) {
                        System.out.println("Employee Options:");
                        System.out.println("1. Add Customer");
                        System.out.println("2. Delete Customer");
                        System.out.println("3. Update Customer");
                        System.out.println("4. List Customers");
                        System.out.println("5. Search Customers");
                        System.out.println("6. Check Offers");
                        System.out.println("7. Cancel Order");
                        System.out.println("8. Make Order");
                        System.out.println("9. Back to Main Menu");

                        int empChoice = getUserChoice(scanner);

                        switch (empChoice) {
                            case 1:
                                System.out.print("Enter Customer Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter Password: ");
                                String password = scanner.nextLine();
                                users newCustomer = new users();
                                newCustomer.setName(name);
                                newCustomer.setPassword(password);
                                employee.addCustomers(newCustomer);
                                break;
                            case 2:
                                System.out.print("Enter Customer ID to delete: ");
                                int id = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                employee.deleteCustomers(id);
                                break;
                            case 3:
                                System.out.print("Enter Customer ID to update: ");
                                id = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                System.out.print("Enter new Name: ");
                                name = scanner.nextLine();
                                employee.updateCustomers(id, name);
                                break;
                            case 4:
                                List<users> customers = employee.listCustomers();
                                if (customers.isEmpty()) {
                                    System.out.println("No customers available.");
                                } else {
                                    System.out.println("Customer List:");
                                    for (users customer : customers) {
                                        System.out.println("ID: " + customer.getId() + ", Name: " + customer.getName());
                                    }
                                }
                                break;
                            case 5:
                                System.out.print("Enter Customer Name to search: ");
                                name = scanner.nextLine();
                                List<users> searchResults = employee.searchCustomers(name);
                                if (searchResults.isEmpty()) {
                                    System.out.println("No customers found.");
                                } else {
                                    System.out.println("Search Results:");
                                    for (users customer : searchResults) {
                                        System.out.println("ID: " + customer.getId() + ", Name: " + customer.getName());
                                    }
                                }
                                break;
                            case 6:
                                employee.checkOffers();
                                break;
                            case 7:
                                System.out.print("Enter Order ID to cancel: ");
                                int orderId = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                employee.cancelOrder(orderId);
                                break;
                            case 8:
                                System.out.print("Enter Customer ID: ");
                                int customerId = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                System.out.print("Enter Order Details: ");
                                String orderDetails = scanner.nextLine();
                                System.out.print("Enter Total Price: ");
                                double totalPrice = scanner.nextDouble();
                                scanner.nextLine(); // Clear buffer
                                employee.makeOrder(customerId, orderDetails, totalPrice);
                                break;
                            case 9:
                                System.out.println("Returning to the main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }

                        if (empChoice == 9) {
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

    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the input buffer
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
        }
        return choice;
    }
}
