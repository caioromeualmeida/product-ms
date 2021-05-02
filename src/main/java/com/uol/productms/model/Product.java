package com.uol.productms.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    private String id = UUID.randomUUID().toString();;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;
}
