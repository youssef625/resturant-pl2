package org.example;

import classes.Customer;
import classes.adminstrator;
import classes.userTypes;
import classes.users;

import java.security.NoSuchAlgorithmException;

public class customerPortal {
    Customer customer = null;

    void customerLogin()   {
        try {
            users user = new users();
            login.isAuthentic(user , userTypes.customer);
            customer = new Customer(user);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }


    }


    public customerPortal() {
        System.out.println("Welcome to the customer portal!");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        try {
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return;

            }
        } catch (Exception e) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

    }
    void register() {
        System.out.println("Enter your userName: ");
        String name = System.console().readLine();
        System.out.println("Enter your password: ");
        String password = System.console().readLine();
        try {
            String hashed = login.hashPassword(password);
            customer = new Customer(name, hashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    void customerMenu() {
        System.out.println("Welcome " + customer.getName() + "!");
        System.out.println("Please select an option:");
        System.out.println("1. View products");
        System.out.println("2. View orders");
        System.out.println("3. Logout");
        try {
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                  //  viewProducts();
                    break;
                case 2:
                  //  viewOrders();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return;
            }
        } catch (Exception e) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    }
}
