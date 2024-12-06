package classes;

import java.sql.Connection;

public class users {
   protected int id;
    protected String name;
    protected String type;

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public boolean updateMyPassword(String password ){

       return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }
}
