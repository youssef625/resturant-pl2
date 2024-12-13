package classes;


import org.example.login;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends users {
    public Customer(users user) {
        id = user.getId();
        name = user.getName();
        type = user.getType();
    }
    public Customer(String _name, String _password) {
       String query = "INSERT INTO users (name, type , password) VALUES (?, ? , ?)";
       String hashed;
       try {
           hashed = login.hashPassword(_password);
       } catch (NoSuchAlgorithmException e) {
            return;

       }
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, _name);
            statement.setString(2, userTypes.customer.name());
            statement.setString(3, _password);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            else {
               System.out.println("Error: No ID obtained.");
               return;
            }
            System.out.println("Customer added: " + _name);
        } catch (SQLException e) {
            return;
        }
        name = _name;
        type = userTypes.customer;
    }






}