package com.ICS499.Application.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "deals", schema = "application")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "discountValue")
    private Double discountValue;

    @Column(name = "discountType")
    private String discountType;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "applicableItemIds", length = 1000)
    private String applicableItemIds;

}