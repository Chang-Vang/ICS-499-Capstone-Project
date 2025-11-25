// language: java
package com.ICS499.Application.controllers;

import com.ICS499.Application.entities.Deal;
import com.ICS499.Application.repositories.DealRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/owner/deals")
public class OwnerDealController {

    @Autowired
    private DealRepository dealRepository;

    @GetMapping
    public List<Deal> listAll() {
        return dealRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createDeal(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double discountValue,
            @RequestParam String discountType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String applicableItemIds,
            HttpSession session) {

        if (title == null || title.isBlank()) return ResponseEntity.badRequest().body("Title required");
        if (discountValue == null || discountValue < 0) return ResponseEntity.badRequest().body("Invalid discount");

        Deal d = new Deal();
        return getResponseEntity(title, description, discountValue, discountType, startDate, endDate, applicableItemIds, d);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double discountValue,
            @RequestParam String discountType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String applicableItemIds) {

        Optional<Deal> opt = dealRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Deal d = opt.get();
        return getResponseEntity(title, description, discountValue, discountType, startDate, endDate, applicableItemIds, d);
    }

    private ResponseEntity<?> getResponseEntity(@RequestParam String title, @RequestParam String description, @RequestParam Double discountValue, @RequestParam String discountType, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate, @RequestParam(required = false) String applicableItemIds, Deal d) {
        d.setTitle(title);
        d.setDescription(description);
        d.setDiscountValue(discountValue);
        d.setDiscountType(discountType);
        d.setStartDate(startDate);
        d.setEndDate(endDate);
        d.setApplicableItemIds(applicableItemIds);

        Deal saved = dealRepository.save(d);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        if (!dealRepository.existsById(id)) return ResponseEntity.notFound().build();
        dealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}