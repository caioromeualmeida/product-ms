package com.uol.productms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductFilterDto {
    private Double min_price;
    private Double max_price;
    private String q;
}
