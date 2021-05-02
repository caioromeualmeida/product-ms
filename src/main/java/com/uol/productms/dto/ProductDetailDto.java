package com.uol.productms.dto;

import lombok.Data;

@Data
public class ProductDetailDto {
    private String id;
    private String name;
    private String description;
    private Double price;
}
