package classes;

import java.util.List;

public interface CustomersManagement {
    boolean addCustomers(users users);
    boolean deleteCustomers(int id);
    boolean updateCustomers(users customer) ;
    List<users> listCustomers();
    List<users> searchCustomers(String name);
    users getcustomer(int id);
}