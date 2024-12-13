package org.example;

import classes.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void flush(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println("checking database connection:");

        try {
           Connection connection = db.connect();
            System.out.println("Database connection successful");
             flush();


        }catch (Exception e){
            System.out.println("Error: "+e);
            return;
        }
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("choose your role: ");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");
            int role;
            try {
                role = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid choice");
                continue;
            }

            switch (role) {
                case 1:
                    flush();
                    scanner = null;
                    adminPortal admin = new adminPortal();
                    break;
                case 2:

                case 3:
                    flush();
                    scanner = null;
                    customerPortal customer = new customerPortal();
                    break;

                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }

        }






    }
}