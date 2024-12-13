package classes;

import java.util.List;

public interface MealsManagement {
    boolean addMeal(meal _meal);
    boolean deleteMeal(int id);
    boolean updateMeal(meal _meal);
    List<meal> listMeals();
    List<meal> searchMeal(String name);
    meal getMeal(int id);
}
