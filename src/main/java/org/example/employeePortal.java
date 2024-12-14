package org.example;

import classes.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class employeePortal {
   Employee employee = null;
    void employeeLogin() throws NoSuchAlgorithmException {
        users user = new users();
        login.isAuthentic(user , userTypes.employee);
        employee = new Employee(user);

    }
    public void employeeMenu(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("Welcome " + employee.getName());
            System.out.println("1. orders Management");
            System.out.println("2. Customers Management");
            System.out.println("0. Exit");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice");
                scanner.nextLine();
                continue;
            }

            if ( choice == 1){
                ordersManagement();
            }
            else if (choice == 2){
                customersManagement();
            }
            else if(choice == 0){
                break;
            }

        }
    }

    public employeePortal(){
        try {
            employeeLogin();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }
        employeeMenu();
    }

    void ordersManagement(){
        while (true){
            List<order> orders = employee.getOrders();
            System.out.println("Orders Management");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "Order ID", "Total Price", "customer name", "payment status");
            System.out.println("------------------------------------------------------------");
            if (orders.isEmpty()) {
                System.out.println("No orders found");

            }
            for (order order : orders) {
                System.out.printf("%-10s %-20s %-10s %-10s%n", order.getOrderId(), order.getTotalPrice(), order.getCustomerName(), order.isPaid());
            }
            System.out.println("------------------------------------------------------------");
            System.out.println("1. Add order");
            System.out.println("2. Update order");
            System.out.println("3. Delete order");
            System.out.println("0. Back");
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addOrder();
                        break;
                    case 2:
                        System.out.println("Enter the order ID:");
                        try {
                            int orderId = scanner.nextInt();
                        updateOrder(orderId);}
                        catch (InputMismatchException e){
                            System.out.println("Invalid input");
                        }
                        break;
                    case 3:
                        deleteOrder();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }


            }catch (InputMismatchException e){
                System.out.println("Invalid choice");
                continue;
            }
        }



    }

    private void deleteOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the order ID:");
        int orderId = scanner.nextInt();
        employee.deleteOrder(orderId);

    }

    private void updateOrder(int orderId) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            List<meal> mealsInOrder = employee.getOrderMeals(orderId);
            try {

                System.out.printf("%-10s %-20s %-10s %-10s%n", "Meal ID", "Meal Name", "Price","Discount", "Quantity");
                System.out.println("------------------------------------------------------------");
                if (mealsInOrder.isEmpty()) {
                    System.out.println("No meals found in this order");
                }
                for (meal meal : mealsInOrder) {
                    System.out.printf("%-10s %-20s %-10s %-10s%n", meal.mealId, meal.mealName, meal.mealPrice,meal.discount+"%", meal.quantity);
                }
                System.out.println("------------------------------------------------------------");
                System.out.println("1. Add meal");
                System.out.println("2. Update meal quantity");
                System.out.println("3. Delete meal");
                System.out.println("0. Back");
                try {
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            int mealId = selectMeal();
                            if (mealId == 0) {
                                break;
                            }
                            System.out.println("Enter the quantity:");
                            int quantity = scanner.nextInt();
                            employee.addMealToOrder(orderId, mealId, quantity);
                            new order(orderId).updateTotalPrice();
                            break;
                        case 2:
                            System.out.println("Enter the meal ID:");
                            mealId = scanner.nextInt();
                            System.out.println("Enter the quantity:");
                            quantity = scanner.nextInt();
                            employee.updateMealInOrder(orderId, mealId, quantity);
                            new order(orderId).updateTotalPrice();
                            break;
                        case 3:
                            System.out.println("Enter the meal ID:");
                            mealId = scanner.nextInt();
                            employee.deleteMealFromOrder(orderId, mealId);
                            new order(orderId).updateTotalPrice();
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }

                }catch (InputMismatchException e){
                    System.out.println("Invalid choice");
                    continue;
                }




            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
                break;
            }
        }


    }

    private boolean addOrder(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("customer list:");
        List<users> users = employee.listCustomers();
        System.out.printf("%-10s %-20s%n", "Customer ID", "Customer Name");
        System.out.println("------------------------------------------------------------");
        if (users.isEmpty()) {
            System.out.println("No customers found");
            return false;
        }
        for (users user : users) {
            System.out.printf("%-10s %-20s%n", user.getId(), user.getName());
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Enter the customer ID:");
        int customerId = scanner.nextInt();
        employee.makeOrder(customerId);
        return true;


    }
    private int selectMeal(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1-get all meals meal:");
        System.out.println("2-search meal:");
        System.out.println("3- get discounted meals:");
        System.out.println("0- back");
        int choice = scanner.nextInt();
        List<meal> meals = null;
        switch (choice){
            case 1:
                meals  = employee.listMeals();
                break;
            case 2:
                System.out.println("Enter the meal name:");
                String name = scanner.nextLine();
                meals = employee.searchMeal(name);
                break;
            case 3:
                meals = employee.listDiscountedMeals();
                break;
            case 0:
                return 0;
            default:
                System.out.println("Invalid choice");
                return selectMeal();
        }
        System.out.printf("%-10s %-20s %-10s %-10s%n", "Meal ID", "Meal Name", "Price","Discount");
        System.out.println("------------------------------------------------------------");
        if (meals.isEmpty()) {
            System.out.println("No meals found");
            return 0;
        }
        for (meal meal : meals) {
            System.out.printf("%-10s %-20s %-10s %-10s%n", meal.mealId, meal.mealName, meal.mealPrice,meal.discount);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Enter the meal ID (enter 0 to cancel):");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.nextLine();
            return 0;
        }

    }


    void customersManagement(){
        Scanner scanner = new Scanner(System.in);
        String searchedcustomers = null;
        while (true){
            List<users> customers = searchedcustomers == null ? employee.listCustomers() : employee.searchCustomers(searchedcustomers);
            if (searchedcustomers == null) {
                System.out.println("customers");
            } else {
                System.out.println("Searched customers for " + searchedcustomers);
            }
            System.out.printf("%-10s %-20s %-10s%n", "customer ID", "Name");
            System.out.println("-------------------------------------------------------------");
            if (customers.isEmpty())
                System.out.println("No customers found");
            for (users customer : customers) {
                System.out.printf("%-10d %-20s %-10s%n", customer.getId(), customer.getName());
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("1- Add customer");
            System.out.println("2- Edit customer");
            System.out.println("3- Delete customer");
            System.out.println("4- Search customer");
            if (searchedcustomers != null)
                System.out.println("5- Clear search");
            System.out.println("0- Exit");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid choice");
                continue;
            }

            switch (choice) {
                case 1:
                    users customer = new users();
                    System.out.print("Enter customer name: ");
                    customer.setName(System.console().readLine());

                    customer.setType( userTypes.customer);
                    while (true){
                        System.out.print("enter customer password:");
                        customer.setPassword(System.console().readLine());
                        System.out.print("enter customer password again:");
                        if (customer.getPassword().equals(System.console().readLine()) )
                            break;
                        System.out.println("the password is incorrect");
                    }
                    if (!employee.addCustomers(customer))
                        System.out.println("Failed to add customer");
                    else{
                        Main.flush();
                        System.out.println("customer added successfully ");
                    }


                    break;
                case 2:
                    System.out.println("Enter customer ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        users emp = employee.getcustomer(id);
                        if (emp == null) {
                            System.out.println("customer not found");
                            continue;
                        }
                        System.out.println("Customer details:");
                        System.out.printf("%-10s %-20s %-10s%n", "customer ID", "Name");
                        System.out.println("-------------------------------------------------------------");
                        System.out.printf("%-10d %-20s%n", emp.getId(), emp.getName());
                        System.out.println("1- Edit name");
                        System.out.println("0- Exit");
                        int editChoice;
                        try {
                            editChoice = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid choice");
                            continue;
                        }
                        switch (editChoice) {
                            case 1:
                                System.out.println("Enter new name: ");
                                emp.setName(System.console().readLine());
                                break;

                            case 0:
                                break;
                        }
                        if (!employee.updateCustomers(emp))
                            System.out.println("Failed to update customer");
                        else
                            Main.flush();
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }

                    break;
                case 3:
                    System.out.println("Enter customer ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        if(employee.deleteCustomers(id))
                            System.out.println("customer deleted successfully");
                        else
                            System.out.println("Failed to delete customer");
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }
                    break;
                case 4:
                    System.out.println("Enter customer name: ");
                    searchedcustomers = System.console().readLine();
                    break;
                case 5:
                    searchedcustomers = null;
                    break;
                case 0:
                    return;
            }
        }
    }


}