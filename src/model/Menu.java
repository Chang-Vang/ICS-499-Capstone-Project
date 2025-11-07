package edu.metrostate.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int menuID = 0;
    private String category;
    private ArrayList<BasicFoodItem> foodItems;

    public Menu(){
        this.menuID = 0;
        this.foodItems = new ArrayList<>();
    }

    public Menu(String category, ArrayList<BasicFoodItem> foodItems){
        this.menuID = menuID++;
        this.category = category;
        this.foodItems = foodItems;

    }

    public int getMenuID(){
        return menuID;
    }

    public void addItem(BasicFoodItem newItem){
        foodItems.add(newItem);
    }

    public List<BasicFoodItem> getFoodItems() {
        return foodItems;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  menuID: %d\n", menuID));
        for (BasicFoodItem item : foodItems) {
            builder.append(item.toString());
        }

        return builder.toString();
    }
}
