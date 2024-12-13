package classes;

import java.util.List;

public interface EmployeesManagement {
   boolean addEmp(users _user);
   boolean deleteEmp(int id);
   boolean updateEmp( users _user);
   List<users> listEmps();
   List<users> searchEmp(String name);
   users getEmp(int id);
}
