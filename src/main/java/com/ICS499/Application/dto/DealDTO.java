package com.ICS499.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealDTO {
    public Long id;
    public String title;
    public String description;
    public Double discountValue;
    public String discountType;
    public String startDate;
    public String endDate;
    public String applicableItemIds;
}
