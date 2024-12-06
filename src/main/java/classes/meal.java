package classes;

public class meal {
    int mealId;
    String mealName;
    float mealPrice;
    float discount;
    boolean setDiscount(){
        return true;
    }
    float getInialPrice(){
        return mealPrice;
    }
    void setPrice(float price){
        mealPrice = price;
    }
    float getFinalPrice(){
        return mealPrice * (discount/100);
    }
}
