package classes;

import org.example.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Employee extends users implements CustomersManagement {

    @Override
    public boolean addCustomers(users customer) { // تعديل التوقيع ليُرجع Boolean
        String password = " ";
        String insertQuery = "INSERT INTO Customers (name, password) VALUES (?, ?)";

        try (Connection connection = db.connect();
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            // Hash the password
            try {
                password = login.hashPassword(customer.getPassword());
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Error: Hashing algorithm not found!");
                e.printStackTrace();
                return false; // إرجاع false عند حدوث خطأ في التشفير
            }

            // Set the parameters
            insertStmt.setString(1, customer.getName());
            insertStmt.setString(2, password);

            // Execute the insertion
            insertStmt.executeUpdate();
            System.out.println("Customer added successfully: " + customer.getName());
            return true; // إرجاع true عند نجاح العملية

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not add customer!");
            e.printStackTrace();
            return false; // إرجاع false عند حدوث خطأ في قاعدة البيانات
        }
    }

    @Override
    public void deleteCustomers(int id) {
        // Not implemented yet
    }

    @Override
    public void updateCustomers(int id, String name) {
        // Not implemented yet
    }

    @Override
    public void listCustomers() {
        // Not implemented yet
    }

    @Override
    public void searchCustomers(String name) {
        // Not implemented yet
    }

    public void makepayment() {
        // Not implemented yet
    }

    void checkOffers() {
        // Not implemented yet
    }
}
