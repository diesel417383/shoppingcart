package com.chemin.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String price;

    @NotBlank
    private String stock;

    @NotBlank
    private String images;

    @NotBlank
    private String category;
}
