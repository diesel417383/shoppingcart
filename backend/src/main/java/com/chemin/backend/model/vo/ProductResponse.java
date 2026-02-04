package com.chemin.backend.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
    
    private int price;

    @NotBlank
    private String stock;

    @NotBlank
    private String images;

    @NotBlank
    private String category;
}
