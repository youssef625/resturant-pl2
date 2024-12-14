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

    public Employee(users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.type = user.getType();
    }
    @Override
    public boolean addCustomers(users customer) {
        String password = " ";
        String insertQuery = "INSERT INTO users (name, password) VALUES (?, ?)";

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
    public boolean deleteCustomers(int id) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";

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
            return false;
        }
        return true;
    }



    @Override
    public List<users> listCustomers() {
        String selectQuery = "SELECT id, name  FROM users where type = 'customer'";
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
        String searchQuery = "SELECT id, name FROM users WHERE name LIKE ?";
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

    public void makeOrder(int customerId) {
        String query = "INSERT INTO Orders (cutomerId, empId) VALUES (?, ?)";
        try (Connection connection = db.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, this.id);
            stmt.executeUpdate();
            System.out.println("Order created successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not create order!");
            e.printStackTrace();
        }


    }

    public List<order> getOrders() {
        String fetchOrdersQuery = "SELECT orderId,cutomerId,totalPrice,empId , isPaid, users.name as name FROM Orders " +
                "join users on users.id = Orders.cutomerId " +
                " where  empId = ? ";
        List<order> ordersList = new ArrayList<>();

        try (Connection connection = db.connect();
             PreparedStatement ordersStmt = connection.prepareStatement(fetchOrdersQuery);
             ) {
            ordersStmt.setInt(1, this.id);

            ResultSet resultSet = ordersStmt.executeQuery();

            while (resultSet.next()) {
                order _order = new order();
                _order.setOrderId(resultSet.getInt("orderId"));
                _order.setCustomerName(resultSet.getString("name"));
                _order.setTotalPrice(resultSet.getFloat("totalPrice"));
                _order.setEmpId(resultSet.getInt("empId"));
                _order.setPaid(resultSet.getBoolean("isPaid"));
                ordersList.add(_order);
            }

            System.out.println("Orders retrieved successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not retrieve orders!");
            e.printStackTrace();
        }
        return ordersList;
    }

    public boolean deleteOrder(int orderId) {
        String deleteOrderQuery = "DELETE FROM Orders WHERE orderId = ? AND empId = ?";

        try (Connection connection = db.connect();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteOrderQuery)) {

            // Set the parameters for the query
            deleteStmt.setInt(1, orderId);
            deleteStmt.setInt(2, this.id);

            // Execute the deletion
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order deleted successfully. ID: " + orderId);
                return true;
            } else {
                System.out.println("No order found with ID: " + orderId);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not delete order!");
            e.printStackTrace();
            return false;
        }
    }

    public List<meal> getOrderMeals(int orderId) {

        String fetchMealsQuery = "SELECT meals.mealId, meals.Name, meals.Price, meals.discount, orderDetails.quantity " +
                "FROM orderDetails " +
                "JOIN meals ON orderDetails.mealId = meals.mealId " +
                "WHERE orderDetails.orderId = ?";
        List<meal> mealsList = new ArrayList<>();

        try (Connection connection = db.connect();
             PreparedStatement mealsStmt = connection.prepareStatement(fetchMealsQuery)) {

            // Set the parameter for the query
            mealsStmt.setInt(1, orderId);

            // Execute the query
            ResultSet resultSet = mealsStmt.executeQuery();

            // Loop through the results
            while (resultSet.next()) {
                meal _meal = new meal();
                _meal.mealId = resultSet.getInt("mealId");
                _meal.mealName = resultSet.getString("Name");
                _meal.mealPrice = resultSet.getFloat("Price");
                _meal.discount = resultSet.getFloat("discount");
                _meal.quantity = resultSet.getFloat("quantity");

                mealsList.add(_meal);
            }

            System.out.println("Meals retrieved successfully for order ID: " + orderId);
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not retrieve meals for order!");
            e.printStackTrace();
        }
        return mealsList;
    }

    public List<meal> listMeals() {
        List<meal> meals = new ArrayList<>();
        String query = "SELECT mealId, name, price, discount FROM meals";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                meal _meal = new meal();
                _meal.mealId = resultSet.getInt("mealId");
                _meal.mealName = resultSet.getString("name");
                _meal.mealPrice = resultSet.getFloat("price");
                _meal.discount = resultSet.getFloat("discount");
                meals.add(_meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    public List<meal> searchMeal(String name) {
        List<meal> meals = new ArrayList<>();
        String query = "SELECT mealId, name, price, discount FROM meals WHERE name LIKE ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    meal _meal = new meal();
                    _meal.mealId = resultSet.getInt("mealId");
                    _meal.mealName = resultSet.getString("name");
                    _meal.mealPrice = resultSet.getFloat("price");
                    _meal.discount = resultSet.getFloat("discount");
                    meals.add(_meal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    public List<meal> listDiscountedMeals() {
        List<meal> meals = new ArrayList<>();
        String query = "SELECT mealId, name, price, discount FROM meals WHERE discount > 0";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                meal _meal = new meal();
                _meal.mealId = resultSet.getInt("mealId");
                _meal.mealName = resultSet.getString("name");
                _meal.mealPrice = resultSet.getFloat("price");
                _meal.discount = resultSet.getFloat("discount");
                meals.add(_meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }
    private boolean ifMealExist(int mealId, int orderId) {
        String query = "SELECT * FROM orderDetails WHERE mealId = ? AND orderId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mealId);
            statement.setInt(2, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addMealToOrder(int orderId, int mealId, float quantity) {
        if (ifMealExist(mealId, orderId)) {
            System.out.println("Meal already exists in order. Please update the quantity instead.");
            return false;
        }
        String insertQuery = "INSERT INTO orderDetails (orderId, mealId, quantity) VALUES (?, ?, ?) " ;
        try (Connection connection = db.connect();
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, orderId);
            insertStmt.setInt(2, mealId);
            insertStmt.setFloat(3, quantity);
            insertStmt.executeUpdate();
            System.out.println("Meal added to order successfully.");

            return true;
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not add meal to order!");
            e.printStackTrace();
            return false;
        }
    }

    public void deleteMealFromOrder(int orderId, int mealId) {
        String deleteQuery = "DELETE FROM orderDetails WHERE orderId = ? AND mealId = ?";
        try (Connection connection = db.connect();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, orderId);
            deleteStmt.setInt(2, mealId);
            deleteStmt.executeUpdate();
            System.out.println("Meal deleted from order successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not delete meal from order!");
            e.printStackTrace();
        }
    }

    public void updateMealInOrder(int orderId, int mealId, int quantity) {
        String updateQuery = "UPDATE orderDetails SET quantity = ? WHERE orderId = ? AND mealId = ?";
        try (Connection connection = db.connect();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, orderId);
            updateStmt.setInt(3, mealId);
            updateStmt.executeUpdate();
            System.out.println("Meal updated in order successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error: Could not update meal in order!");
            e.printStackTrace();
        }
    }
    public users getcustomer(int id) {
        String query = "SELECT id, name, type FROM users WHERE id = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    users user = new users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setType(userTypes.valueOf( resultSet.getString("type")));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCustomers(users customer) {
        String updateQuery = "UPDATE users SET name = ? WHERE id = ?";

        try (Connection connection = db.connect();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Set the parameters
            updateStmt.setString(1, customer.getName());
            updateStmt.setInt(2, customer.getId());

            // Execute the update
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully: " + customer.getName());
            } else {
                System.out.println("No customer found with ID: " + customer.getId());
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: Could not update customer!");
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
