package org.example;

import classes.adminstrator;
import classes.*;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class adminPortal {
    adminstrator admin = null;


    public void adminLogin() throws NoSuchAlgorithmException {
        users user = new users();
        login.isAuthentic(user , "admin");
        admin = new adminstrator(user);

    }
    public void adminMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome "+admin.getName());
        System.out.println("1. Add Meal");
        System.out.println("2. Delete Meal");
        System.out.println("3. Update Meal");
        System.out.println("4. List Meals");
        System.out.println("5. Search Meal");
        System.out.println("6. Add Employee");
        System.out.println("7. Delete Employee");
        System.out.println("8. Update Employee");
        System.out.println("9. List Employees");
        System.out.println("10. Search Employee");
        System.out.println("11. Exit");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice){
            case 1:

                break;
            case 2:
                admin.deleteMeal(1);
                break;
            case 3:
                admin.updateMeal();
                break;
            case 4:
                admin.listMeals();
                break;
            case 5:
                admin.searchMeal();
                break;
            case 6:
                admin.addEmp(1,"John Doe");
                break;
            case 7:
                admin.deleteEmp(1);
                break;
            case 8:
                admin.updateEmp();
                break;
            case 9:
                admin.listEmps();
                break;
            case 10:
                admin.searchEmp();
                break;
            case 11:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
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


}
