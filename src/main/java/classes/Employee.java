package classes;


import org.example.login;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Employee extends users implements CustomersManagement {

    @Override
    public boolean addCustomers(users customer) {
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
                return false;
            }

            // Set the parameters
            insertStmt.setString(1, customer.getName());
            insertStmt.setString(2, password);

            // Execute the insertion
            insertStmt.executeUpdate();
            System.out.println("Customer added successfully: " + customer.getName());
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not add customer!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteCustomers(int id) {
        String deleteQuery = "DELETE FROM Customers WHERE id = ?";

        try (Connection connection = db.connect();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {

            // Set the ID parameter
            deleteStmt.setInt(1, id);

            // Execute the deletion
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully with ID: " + id);
            } else {
                System.out.println("No customer found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not delete customer!");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomers(int id, String name) {
        String updateQuery = "UPDATE Customers SET name = ? WHERE id = ?";

        try (Connection connection = db.connect();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            updateStmt.setString(1, name);
            updateStmt.setInt(2, id);

            // Execute the update
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully. ID: " + id + ", New Name: " + name);
            } else {
                System.out.println("No customer found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not update customer!");
            e.printStackTrace();
        }
    }


    @Override
    public List<users> listCustomers() {
        String selectQuery = "SELECT id, name FROM Customers";
        List<users> customersList = new ArrayList<>();

        try (Connection connection = db.connect();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             ResultSet resultSet = selectStmt.executeQuery()) {

            // Loop through the result set
            while (resultSet.next()) {
                users customer = new users();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customersList.add(customer);
            }

            System.out.println("List of customers retrieved successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not retrieve customer list!");
            e.printStackTrace();
        }

        return customersList;
    }


    @Override
    public List<users> searchCustomers(String name) {
        String searchQuery = "SELECT id, name FROM Customers WHERE name LIKE ?";
        List<users> customersList = new ArrayList<>();

        try (Connection connection = db.connect();
             PreparedStatement searchStmt = connection.prepareStatement(searchQuery)) {

            // Set the search parameter
            searchStmt.setString(1, "%" + name + "%");

            // Execute the query
            ResultSet resultSet = searchStmt.executeQuery();

            // Loop through the results
            while (resultSet.next()) {
                users customer = new users();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customersList.add(customer);
            }

            System.out.println("Search completed successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not search customers!");
            e.printStackTrace();
        }

        return customersList;
    }



    public void checkOffers() {
        String fetchOffersQuery = "SELECT id, description, discount, valid_until FROM Offers WHERE valid_until >= GETDATE()";

        try (Connection connection = db.connect();
             PreparedStatement offersStmt = connection.prepareStatement(fetchOffersQuery);
             ResultSet resultSet = offersStmt.executeQuery()) {

            System.out.println("Available Offers:");
            boolean hasOffers = false;

            while (resultSet.next()) {
                hasOffers = true;
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                double discount = resultSet.getDouble("discount");
                String validUntil = resultSet.getString("valid_until");

                System.out.println("Offer ID: " + id);
                System.out.println("Description: " + description);
                System.out.println("Discount: " + discount + "%");
                System.out.println("Valid Until: " + validUntil);
                System.out.println("----------------------------");
            }

            if (!hasOffers) {
                System.out.println("No offers are currently available.");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not retrieve offers!");
            e.printStackTrace();
        }
    }
    public void makeOrder(int customerId, String orderDetails, double totalPrice) {
        String insertOrderQuery = "INSERT INTO Orders (customer_id, order_details, total_price, order_date) VALUES (?, ?, ?, GETDATE())";

        try (Connection connection = db.connect();
             PreparedStatement orderStmt = connection.prepareStatement(insertOrderQuery)) {

            // Set the parameters for the order
            orderStmt.setInt(1, customerId);
            orderStmt.setString(2, orderDetails);
            orderStmt.setDouble(3, totalPrice);

            // Execute the insertion
            int rowsAffected = orderStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order placed successfully for Customer ID: " + customerId);
            } else {
                System.out.println("Failed to place the order for Customer ID: " + customerId);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not place order!");
            e.printStackTrace();
        }
    }

    public void cancelOrder(int orderId) {
        String deleteOrderQuery = "DELETE FROM Orders WHERE id = ?";

        try (Connection connection = db.connect();
             PreparedStatement cancelStmt = connection.prepareStatement(deleteOrderQuery)) {

            // Set the ID parameter
            cancelStmt.setInt(1, orderId);

            // Execute the deletion
            int rowsAffected = cancelStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order canceled successfully with ID: " + orderId);
            } else {
                System.out.println("No order found with ID: " + orderId);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not cancel the order!");
            e.printStackTrace();
        }
    }






}
