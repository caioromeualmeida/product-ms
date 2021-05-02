package com.uol.productms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Description is required.")
    @NotNull(message = "Description is required.")
    private String description;

    @NotNull(message = "Price is required.")
    @Positive(message = "Must be a positive value.")
    private Double price;
}
