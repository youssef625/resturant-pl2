package classes;

public class adminstrator extends users implements MealsManagement, EmployeesManagement {

    public adminstrator(users user) {
        id = user.getId();
        name = user.getName();
        type = user.getType();

    }

    public void addMeal(int id, String name, String type, double price) {
    }

    public void deleteMeal(int id) {
    }

    public void updateMeal() {
    }

    public void listMeals() {
    }

    public void searchMeal() {
    }

    public void addEmp(int id, String name) {
    }

    public void deleteEmp(int id) {
    }

    public void updateEmp() {
    }

    public void listEmps() {
    }

    public void searchEmp() {
    }
}
