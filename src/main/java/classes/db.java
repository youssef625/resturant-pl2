package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=res;trustServerCertificate=true;loginTimeout=30";
    private static final String USER = "sa";
    private static final String PASSWORD = "P@ssw0rd";
    private static Connection instance = null;
    public static   Connection connect() {
        if (instance != null) {
            return instance;
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to MSSQL database established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to MSSQL database.");
            e.printStackTrace();
        }
        instance = connection;

        return instance;
    }




}