package classes;

public class meal {
    public int mealId;
    public String mealName;
    public float mealPrice;
    public float discount;
    public float quantity = 0;


    public boolean setDiscount(){
        return true;
    }
    public float getInialPrice(){
        return mealPrice;
    }
    public void setPrice(float price){
        mealPrice = price;
    }
    public float getFinalPrice(){
        return mealPrice * (discount/100);
    }
}
