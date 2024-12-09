package classes;

import java.util.List;

public interface CustomersManagement {
    void addCustomers(int id, String name);
    void deleteCustomers(int id);
    void updateCustomers(int id, String name);
    List<users> listCustomers();
    void searchCustomers(String name);
}