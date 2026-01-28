package com.chemin.backend.dto.response;

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

    @NotBlank
    private String price;

    @NotBlank
    private String stock;

    @NotBlank
    private String images;

    @NotBlank
    private String category;

}
