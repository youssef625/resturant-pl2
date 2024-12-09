package classes;

public interface CustomersManagement {
    boolean addCustomers(users users);
    void deleteCustomers(int id);
    void updateCustomers(int id, String name);
    void listCustomers();
    void searchCustomers(String name);
}