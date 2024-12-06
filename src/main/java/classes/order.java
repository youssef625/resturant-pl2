package classes;

public class order {
    private int orderId;
    private meal[] orderDetails;
    private float totalPrice;

    public order(int orderId, meal[] orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.totalPrice = calculateTotalPrice();
    }

    public void createOrder(int orderId, meal[] orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.totalPrice = calculateTotalPrice();
        System.out.println("Order created with ID: " + orderId);
    }

    public void updateOrder(meal[] orderDetails) {
        this.orderDetails = orderDetails;
        this.totalPrice = calculateTotalPrice();
        System.out.println("Order updated with ID: " + orderId);
    }

    public void cancelOrder() {
        this.orderDetails = null;
        this.totalPrice = 0;
        System.out.println("Order cancelled with ID: " + orderId);
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    private float calculateTotalPrice() {
        float total = 0;
        if (orderDetails != null) {
            for (meal m : orderDetails) {
                total += m.getFinalPrice();
            }
        }
        return total;
    }
}