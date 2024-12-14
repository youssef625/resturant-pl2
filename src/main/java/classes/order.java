package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class order {
    private int orderId;
    private float totalPrice;
    private int empId;
    private int cutomerId;
    private boolean isPaid;
    private String customerName;

    public order(int orderId) {
        this.orderId = orderId;

        //this.totalPrice = calculateTotalPrice();
    }

    public order(int orderId, float totalPrice, int empId, int cutomerId, boolean isPaid) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.empId = empId;
        this.cutomerId = cutomerId;
        this.isPaid = isPaid;
    }
    public order() {
    }

    private float calculateTotalPrice() {
        float total = 0;

        return total;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getCutomerId() {
        return cutomerId;
    }

    public void setCutomerId(int cutomerId) {
        this.cutomerId = cutomerId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public static order findOrder(List<order> orders, int orderId) {
        for (order _order : orders) {
            if (_order.getOrderId() == orderId) {
                return _order;
            }
        }
        return null;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void updateTotalPrice(){
        List<meal> meals = new ArrayList<>();
        String query = "SELECT meals.mealId, name, price, discount , quantity FROM meals JOIN orderDetails ON meals.mealId = orderDetails.mealId WHERE orderId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    meal _meal = new meal();
                    _meal.mealId = resultSet.getInt("mealId");
                    _meal.mealName = resultSet.getString("name");
                    _meal.mealPrice = resultSet.getFloat("price");
                    _meal.discount = resultSet.getFloat("discount");
                    _meal.quantity = resultSet.getInt("quantity");
                    meals.add(_meal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (meal _meal : meals) {
            totalPrice += _meal.getFinalPrice() * _meal.quantity;
        }
        query = "UPDATE orders SET totalPrice = ? WHERE orderId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setFloat(1, totalPrice);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean isOrderExist(int orderId) {
        String query = "SELECT * FROM orders WHERE orderId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


