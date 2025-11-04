package edu.metrostate.model;

public class Restaurant {
    private int id;
    private String name;
    private String location;
    private String category;
    private Menu menu;

    public Restaurant(String name, String location, String category, Menu menu){
        this.name = name;
        this.location = location;
        this.category = category;
        this.menu = menu;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setLocation(String l){
        location = l;
    }

    public String getLocation(){
        return location;
    }

    public void setCategory(String c){
        category = c;
    }

    public String getCategory(){
        return category;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setID(int newID) {
        this.id = newID;
    }

    public int getID() {
        return  id;
    }

    public String toString() {
        String builder = String.format("ID: %d, name: %s, location: %s, category: %s\n", id, name, location, category);
        builder += menu.toString();
        return builder;
    }
}
