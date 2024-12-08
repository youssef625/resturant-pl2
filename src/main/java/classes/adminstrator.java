package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class adminstrator extends users implements MealsManagement, EmployeesManagement {

    public adminstrator(users user) {
        id = user.getId();
        name = user.getName();
        type = user.getType();

    }



    @Override
    public boolean addMeal(meal _meal) {
        String query = "INSERT INTO meals ( name, price, discount) VALUES ( ?, ?, ?)";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, _meal.mealId);
            statement.setString(1, _meal.mealName);
            statement.setFloat(2, _meal.mealPrice);
            statement.setFloat(3, _meal.discount);
            statement.executeUpdate();
            System.out.println("Meal added: " + _meal.mealName);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMeal(int id) {
        String query = "DELETE FROM meals WHERE mealId = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Meal deleted: " + id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMeal(meal _meal) {
return false;

    }

    @Override
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

    @Override
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
    @Override
    public boolean addEmp(users _user) {

        return false;
    }

    @Override
    public boolean deleteEmp(int id) {
        return false;
    }

    @Override
    public boolean updateEmp(users _user) {
        return false;
    }

    @Override
    public List<users> listEmps() {
        List<users> users = new ArrayList<>();
        String query = "SELECT id, name, type FROM users WHERE type in ('employee', 'admin') or type = 'admin'";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users user = new users();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setType(resultSet.getString("type"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<users> searchEmp(String name) {
            List<users> users = new ArrayList<>();
            String query = "SELECT id, name, type FROM users WHERE (type in ('employee', 'admin')) AND name LIKE ?";

            try (Connection connection = db.connect();

                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "%" + name + "%");
                ResultSet resultSet = statement.executeQuery() ;
                while (resultSet.next()) {
                    users user = new users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setType(resultSet.getString("type"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
    }
}
