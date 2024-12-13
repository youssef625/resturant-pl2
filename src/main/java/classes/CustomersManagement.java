package classes;

import java.util.List;

public interface CustomersManagement {
    boolean addCustomers(users users);
    void deleteCustomers(int id);
    void cancelOrder(int orderId);
    void makeOrder(int customerId, String orderDetails, double totalPrice);
    void updateCustomers(int id, String name);
    List<users> listCustomers();
    List<users> searchCustomers(String name);
}