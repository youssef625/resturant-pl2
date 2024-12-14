package org.example;

import classes.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class customerPortal {
    Customer customer = null;

    boolean customerLogin()   {
        try {
            users user = new users();
            if(!login.isAuthentic(user , userTypes.customer))
                return false;
            customer = new Customer(user);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return true;


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

                    if(!customerLogin())
                        return;
                    break;
                case 2:
                    if(!register())
                        return;
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
        customerMenu();

    }
    boolean register() {
        System.out.println("Enter your userName: ");
        String name = System.console().readLine();
        System.out.println("Enter your password: ");
        String password = System.console().readLine();
        try {
            String hashed = login.hashPassword(password);
            customer = new Customer(name, hashed);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

    }

    void customerMenu() {
        while (true){
            Main.flush();
            System.out.println("Welcome " + customer.getName() + "!");
            System.out.println("Please select an option:");
            System.out.println("1. View orders");
            System.out.println("2. Edit profile");
            System.out.println("0. Logout");
            try {
                int choice = Integer.parseInt(System.console().readLine());
                if  (choice == 0) {
                    return;
                } else if (choice == 1) {
                    viewOrders();
                } else if (choice == 2) {
                    editProfile();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }
        }

    }
    void viewOrders() {
        while (true){
            Main.flush();
            List<order> orders = customer.listOrders();
            System.out.printf("%-10s %-20s %-10s %-10s%n", "Order ID", "Total Price", "Employee ID", "payment Status");
            System.out.println("-------------------------------------------------------------");
            if(orders.isEmpty()){
                System.out.println("No orders found.");
                return;}
            for (order _order : orders) {
                System.out.printf("%-10s %-20s %-10s %-10s%n", _order.getOrderId(), _order.getTotalPrice(), _order.getEmpId(), _order.isPaid());
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("choose an order to pay for or -1 to exit:");

            try {
                int orderID = Integer.parseInt(System.console().readLine());
                if (orderID == -1) {
                    break;
                }
                order searchOrder = order.findOrder(orders, orderID);
                if (searchOrder == null) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;

                }else if (searchOrder.isPaid()) {
                    System.out.println("Order already paid for.");
                    continue;
                } else if (orderID == -1) {
                    break;

                }

                customer.makepayment(orderID);

            } catch (Exception e) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }


        }



    }

    void editProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Change name");
        System.out.println("2- Change password");
        System.out.println("0- Exit");

        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception var5) {
            System.out.println("Invalid choice");
            return;
        }

        switch (choice) {
            case 0:
                return;
            case 1:
                System.out.println("Enter new name: ");
                String name = System.console().readLine();
                if (this.customer.changeMyName(name)) {
                    System.out.println("Name updated successfully");
                } else {
                    System.out.println("Failed to update name");
                }
                break;
            case 2:
                System.out.println("Enter new password: ");
                String password = System.console().readLine();
                if (this.customer.changeMyPassword(password)) {
                    System.out.println("Password updated successfully");
                } else {
                    System.out.println("Failed to update password");
                }
        }

    }

}
