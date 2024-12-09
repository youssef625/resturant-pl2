package classes;

import java.util.List;

public interface CustomersManagement {
    boolean addCustomers(users users);
    void deleteCustomers(int id);
    void updateCustomers(int id, String name);
    List<users> listCustomers();
    void searchCustomers(String name);
}