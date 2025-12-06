package com.ICS499.Application.controllers;

import com.ICS499.Application.Restaurant;
import com.ICS499.Application.dto.DealDTO;
import com.ICS499.Application.entities.Deal;
import com.ICS499.Application.repositories.DealRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owner/deals")
public class OwnerDealController {

    @Autowired
    private DealRepository dealRepository;

//    Returning deals from all restaurants - not appropriate for owner view
//    @GetMapping
//    public List<Deal> listAll() {
//        return dealRepository.findAll();
//    }

    // Return deals only for the restaurant in the session
    @GetMapping
    public List<Deal> listDealsForRestaurant(HttpSession session) {

        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");

        if (restaurant == null) {
            // No restaurant in session → return empty list
            return Collections.emptyList();
        }

        // Fetch deals specifically for this restaurant
        return dealRepository.findAllByRestaurant(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> createDeal(
            @RequestBody DealDTO dto,
            HttpSession session
    ) {

        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null) {
            return ResponseEntity.status(400).body("No active restaurant selected");
        }

        Deal d = new Deal();
        mapDtoToDeal(dto, d);
        d.setRestaurant(restaurant);

        Deal saved = dealRepository.save(d);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(
            @PathVariable Long id,
            @RequestBody DealDTO dto,
            HttpSession session
    ) {

        Optional<Deal> opt = dealRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Deal d = opt.get();

        Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
        if (restaurant == null || d.getRestaurant().getId() != restaurant.getId()) {
            return ResponseEntity.status(403).body("Deal does not belong to your restaurant");
        }

        mapDtoToDeal(dto, d);

        Deal saved = dealRepository.save(d);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        if (!dealRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void mapDtoToDeal(DealDTO dto, Deal d) {
        d.setTitle(dto.title);
        d.setDescription(dto.description);
        d.setDiscountValue(dto.discountValue);
        d.setDiscountType(dto.discountType);
        d.setStartDate(LocalDate.parse(dto.startDate));
        d.setEndDate(LocalDate.parse(dto.endDate));
        d.setApplicableItemIds(dto.applicableItemIds);
    }
}
