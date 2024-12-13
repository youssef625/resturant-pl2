package org.example;

import classes.adminstrator;
import classes.*;

import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class adminPortal {
    adminstrator admin = null;


    void adminLogin() throws NoSuchAlgorithmException {
        users user = new users();
        login.isAuthentic(user , "admin");
        admin = new adminstrator(user);

    }
    public void adminMenu(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("Welcome " + admin.getName());
            System.out.println("1- Meal Management");
            System.out.println("2- Employee Management");
            System.out.println("3- Edit Profile");
            System.out.println("4- create report");
            System.out.println("10- Logout");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    mealManagement();
                    Main.flush();
                    break;
                case 2:
                    employeesManagement();
                    Main.flush();
                    break;
                case 3:
                    editProfile();
                    Main.flush();
                    break;
                case 4:
                    createReport();
                    Main.flush();
                    break;

                case 10:
                    return;

            }
        }
    }

    public adminPortal(){
        try {
            adminLogin();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }
        adminMenu();
    }

    void mealManagement(){
        Scanner scanner = new Scanner(System.in);
        String searchedMeals = null;
        while (true) {
            List<meal> meals = searchedMeals == null ? admin.listMeals() : admin.searchMeal(searchedMeals);
            if (searchedMeals == null) {
                System.out.println("Meals");
            } else {
                System.out.println("Searched meals for " + searchedMeals);
            }
            System.out.printf("%-10s %-20s %-10s %-10s%n", "Meal ID", "Name", "Price", "Discount");
            System.out.println("-------------------------------------------------------------");
            if (meals.isEmpty())
                System.out.println("No meals found");
            for (meal _meal : meals) {
                System.out.printf("%-10d %-20s %-10.2f %-10.2f%n", _meal.mealId, _meal.mealName, _meal.mealPrice, _meal.discount);
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("1- Add Meal");
            System.out.println("2- Edit Meal");
            System.out.println("3- Delete Meal");
            System.out.println("4- Search Meal");
            if (searchedMeals != null)
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
                    meal _meal = new meal();
                    System.out.println("Enter meal name: ");
                    _meal.mealName = System.console().readLine();
                    while (true){
                        try {
                            System.out.println("Enter meal price: ");
                            _meal.mealPrice = scanner.nextFloat();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid value");
                        }
                    }
                    while (true){
                        try {
                            System.out.println("Enter meal discount: ");
                            _meal.discount = scanner.nextFloat();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid value");
                        }
                    }

                    if (!admin.addMeal(_meal))
                        System.out.println("Failed to add meal");
                    else
                        Main.flush();
                    break;
                case 2:
                    System.out.println("Enter meal ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        meal meal = admin.getMeal(id);
                        if (meal == null) {
                            System.out.println("Meal not found");
                            continue;
                        }
                        System.out.printf("%-10s %-20s %-10s %-10s%n", "Meal ID", "Name", "Price", "Discount");
                        System.out.println("-------------------------------------------------------------");
                        System.out.printf("%-10d %-20s %-10.2f %-10.2f%n", meal.mealId, meal.mealName, meal.mealPrice, meal.discount);
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("1- Edit name");
                        System.out.println("2- Edit price");
                        System.out.println("3- Edit discount");
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
                                meal.mealName = System.console().readLine();
                                break;
                            case 2:
                                while (true){
                                    try {
                                        System.out.println("Enter new price: ");
                                        meal.mealPrice = scanner.nextFloat();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Invalid value");
                                    }
                                }
                                break;
                            case 3:
                                while (true){
                                    try {
                                        System.out.println("Enter new discount: ");
                                        meal.discount = scanner.nextFloat();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Invalid value");
                                    }
                                }
                                break;
                            case 0:
                                break;
                        }
                        if (!admin.updateMeal(meal))
                            System.out.println("Failed to update meal");
                        else
                            Main.flush();

                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }
                    break;
                case 3:
                    System.out.println("Enter meal ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        if(admin.deleteMeal(id))
                            System.out.println("Meal deleted successfully");
                        else
                            System.out.println("Failed to delete meal");
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }
                    break;
                case 4:
                    System.out.println("Enter meal name: ");

                    searchedMeals = System.console().readLine();

                    break;
                case 5:
                    searchedMeals = null;
                    break;
                case 0:
                        return;


            }
            Main.flush();

        }

    }
    void employeesManagement(){
        Scanner scanner = new Scanner(System.in);
        String searchedEmployees = null;
        while (true){
            List<users> employees = searchedEmployees == null ? admin.listEmps() : admin.searchEmp(searchedEmployees);
            if (searchedEmployees == null) {
                System.out.println("Employees");
            } else {
                System.out.println("Searched employees for " + searchedEmployees);
            }
            System.out.printf("%-10s %-20s %-10s%n", "Employee ID", "Name", "Type");
            System.out.println("-------------------------------------------------------------");
            if (employees.isEmpty())
                System.out.println("No employees found");
            for (users employee : employees) {
                System.out.printf("%-10d %-20s %-10s%n", employee.getId(), employee.getName(), employee.getType());
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("1- Add Employee");
            System.out.println("2- Edit Employee");
            System.out.println("3- Delete Employee");
            System.out.println("4- Search Employee");
            if (searchedEmployees != null)
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
                    users employee = new users();
                    System.out.print("Enter employee name: ");
                    employee.setName(System.console().readLine());
                    System.out.println("Enter employee type: ");
                    System.out.println("1- Admin");
                    System.out.println("2- Employee");
                    int type = scanner.nextInt();
                    employee.setType(type == 1 ? userTypes.admin : userTypes.employee);
                    while (true){
                        System.out.print("enter employee password:");
                        employee.setPassword(System.console().readLine());
                        System.out.print("enter employee password again:");
                        if (employee.getPassword().equals(System.console().readLine()) )
                            break;
                        System.out.println("the password is incorrect");
                    }
                    if (!admin.addEmp(employee))
                        System.out.println("Failed to add employee");
                    else{
                        Main.flush();
                        System.out.println("employee added successfully ");
                    }


                    break;
                case 2:
                    System.out.println("Enter employee ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        users emp = admin.getEmp(id);
                        if (emp == null) {
                            System.out.println("Employee not found");
                            continue;
                        }
                        System.out.printf("%-10s %-20s %-10s%n", "Employee ID", "Name", "Type");
                        System.out.println("-------------------------------------------------------------");
                        System.out.printf("%-10d %-20s %-10s%n", emp.getId(), emp.getName(), emp.getType());
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("1- Edit name");
                        System.out.println("2- Edit type");
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
                            case 2:
                                System.out.println("Enter new type: ");
                                System.out.println("1- Admin");
                                System.out.println("2- Employee");
                                int changeType = scanner.nextInt();
                                emp.setType(changeType == 1 ? userTypes.admin : userTypes.employee);
                                break;
                            case 0:
                                break;
                        }
                        if (!admin.updateEmp(emp))
                            System.out.println("Failed to update employee");
                        else
                            Main.flush();
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }

                    break;
                case 3:
                    System.out.println("Enter employee ID: ");
                    try {
                        int id = scanner.nextInt();
                        Main.flush();
                        if(admin.deleteEmp(id))
                            System.out.println("Employee deleted successfully");
                        else
                            System.out.println("Failed to delete employee");
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }
                    break;
                case 4:
                    System.out.println("Enter employee name: ");
                    searchedEmployees = System.console().readLine();
                    break;
                case 5:
                    searchedEmployees = null;
                    break;
                case 0:
                    return;
            }
        }
    }
    void editProfile(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Change name");
        System.out.println("2- Change password");
        System.out.println("0- Exit");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice");
            return;
        }
        switch (choice) {
            case 1:
                System.out.println("Enter new name: ");
                String name = System.console().readLine();
                if (admin.changeMyName(name))
                    System.out.println("Name updated successfully");
                else
                    System.out.println("Failed to update name");
                break;
            case 2:
                System.out.println("Enter new password: ");
                String password = System.console().readLine();
                if (admin.changeMyPassword(password))
                    System.out.println("Password updated successfully");
                else
                    System.out.println("Failed to update password");
                break;
            case 0:
                return;
        }
    }
    void createReport(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Generate report");
        System.out.println("0- Exit");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid choice");
            return;
        }
        switch (choice) {
            case 1:
                //admin.generateReport();
                break;
            case 0:
                return;
        }
    }
}
