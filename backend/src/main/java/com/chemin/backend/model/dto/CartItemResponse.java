package com.chemin.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @NotNull
    private Double productPrice;

    private String productImages;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double totalPrice;
}
