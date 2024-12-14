package classes;


import org.example.login;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<order> listOrders() {
        List<order> orders = new ArrayList<order>();
        String query = "SELECT * FROM orders WHERE cutomerId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                order _order = new order();
                _order.setOrderId(resultSet.getInt("orderId"));
                _order.setCutomerId(resultSet.getInt("cutomerId"));
                _order.setTotalPrice(resultSet.getFloat("totalPrice"));
                _order.setEmpId(resultSet.getInt("empId"));
                _order.setPaid(resultSet.getBoolean("isPaid"));
                orders.add(_order);
            }
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }

    public void makepayment(int orderID) {
        String query = "UPDATE orders SET isPaid = 1 WHERE orderId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderID);
            statement.executeUpdate();
            System.out.println("Payment made for order: " + orderID);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }






}