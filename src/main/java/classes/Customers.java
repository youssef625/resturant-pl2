package classes;


import java.util.ArrayList;
import java.util.List;

public class Customers  {

    private class Customer {
        int id;
        String name;

        Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return "Customer ID: " + id + ", Name: " + name;
        }
    }


}