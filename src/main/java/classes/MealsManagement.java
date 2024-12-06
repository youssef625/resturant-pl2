package classes;

public interface MealsManagement {
    void addMeal(int id, String name, String type, double price);
    void deleteMeal(int id);
    void updateMeal();
    void listMeals();
    void searchMeal();
}
