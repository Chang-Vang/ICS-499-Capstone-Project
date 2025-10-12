package edu.metrostate.model;

public class OrderItem {
    private BasicFoodItem food;
    private int quantity;

    public void orderItem(BasicFoodItem newItem, int quantity) {
        this.food = newItem;
        this.quantity = quantity;
    }

    public void setQuantity(int i) {
        quantity = i;
    }

    public int getQuantity() {
        return quantity;
    }

}
