package com.chemin.backend.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(0)
    private int price;

    @NotBlank
    private String stock;

    @NotBlank
    private String images;

    @NotBlank
    private String category;
}
