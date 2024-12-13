package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import classes.*;

public class login {
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
    public static boolean isAuthentic(users user , String type) throws NoSuchAlgorithmException {
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter your username: ");
            String username = System.console().readLine();
            System.out.println("Enter your password: ");
            String password = System.console().readLine();
            if (authenticate(username, password ,user) && user.getType().name().equals(type)) {
                return true;
            }
            else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        return  false;
    }
    public static boolean authenticate(String username, String password ,users user ) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        String query = "SELECT id, name ,type FROM users WHERE name = ? AND password = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                   user.setId(resultSet.getInt("id"));
                   user.setName(resultSet.getString("name"));
                   user.setType(userTypes.valueOf( resultSet.getString("type")));
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                   return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
