package database;

import java.sql.Connection;

public class users {
    private int id;
    private String name;
    private String email;
    private String password;

    public users(int id) {
        Connection connection = new db().connect();

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
