package com.ICS499.Application.controllers;

import com.ICS499.Application.FoodItem;
import com.ICS499.Application.model.OrderItem;
import com.ICS499.Application.repositories.FoodItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CartController {

    @Autowired
    private FoodItemRepository foodItemRepository;

    // Add item to cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("foodId") Long foodId, HttpSession session) {

        FoodItem item = foodItemRepository.findById(foodId).orElse(null);
        if (item == null) { return "redirect:/home"; }

        // cart = Map<foodId, OrderItem>
        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart == null) { cart = new HashMap<>(); }

        OrderItem orderItem = cart.get(foodId);
        if (orderItem == null) {
            orderItem = new OrderItem(item, 1);
        } else {
            orderItem.setQuantity(orderItem.getQuantity() + 1);
        }
        cart.put(foodId, orderItem);

        session.setAttribute("cart", cart);

        return "redirect:/home";
    }

    // View cart
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart == null) { cart = new HashMap<>();}
        //add list of cart items to miodel
        Collection<OrderItem> items = cart.values();
        model.addAttribute("cartItems", items);

        //calculate total cart count
        int cartCount = 0;
        for (OrderItem item : items) {
            cartCount += item.getQuantity();
        }
        model.addAttribute("cartCount", cartCount);

        //total price
        double total = 0;
        for (OrderItem item : items) {
            total += item.getFood().getPrice() * item.getQuantity();
        }
        model.addAttribute("total", total);

        return "cart/cart";
    }

    @PostMapping("/cart/increase")
    public String increaseQuantity(@RequestParam Long foodId, HttpSession session) {
        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(foodId)) {
            OrderItem item = cart.get(foodId);
            item.setQuantity(item.getQuantity() + 1);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/decrease")
    public String decreaseQuantity(@RequestParam Long foodId, HttpSession session) {
        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(foodId)) {
            OrderItem item = cart.get(foodId);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam Long foodId, HttpSession session) {
        Map<Long, OrderItem> cart = (Map<Long, OrderItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(foodId);
        }
        return "redirect:/cart";
    }

}
