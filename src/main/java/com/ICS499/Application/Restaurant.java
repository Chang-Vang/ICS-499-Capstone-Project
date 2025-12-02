package com.ICS499.Application;

import com.ICS499.Application.entities.Deal;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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
    @JsonManagedReference // prevents serializing back into FoodItem.restaurant
    private Set<FoodItem> foodItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference // paired with User.restaurants
    private User owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference("restaurant-deals") // paired with Deal.restaurant
    private List<com.ICS499.Application.entities.Deal> deals;


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