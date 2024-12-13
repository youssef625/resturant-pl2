package classes;

import org.example.login;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class users {
   protected int id;
    protected String name;
    protected userTypes type;
    private String password;

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public boolean changeMyPassword(String _password )  {
        try {
            String hashed = login.hashPassword(_password);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return false;
        }
        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, _password);
            statement.setInt(2, id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }



       return true;
    }

    public boolean changeMyName(String _name) {
        String query = "update users set name = ? where id = ?";
        try (Connection connection = db.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, _name);
            statement.setInt(2, this.id);
            statement.executeUpdate();
            System.out.println("Name updated: " + _name);
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            }
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public userTypes getType() {
        return type;
    }

    public void setType(userTypes type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
