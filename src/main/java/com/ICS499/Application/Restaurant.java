package com.ICS499.Application;

import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

//@Getter
//@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "restaurant")
    private Set<FoodItem> foodItems = new HashSet<>();

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public Set<FoodItem> getFoodItems() {return foodItems;}

    public void setFoodItems(Set<FoodItem> foodItems) {this.foodItems = foodItems;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(category, that.category) && Objects.equals(foodItems, that.foodItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, category, foodItems);
    }
}