package classes;

public interface CustomersManagement {
    void addCustomers(int id, String name);
    void deleteCustomers(int id);
    void updateCustomers(int id, String name);
    void listCustomers();
    void searchCustomers(String name);
}