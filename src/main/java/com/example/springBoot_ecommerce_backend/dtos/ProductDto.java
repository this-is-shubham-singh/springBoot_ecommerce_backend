package com.example.springBoot_ecommerce_backend.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String brandName;
    private int inventory;
}
