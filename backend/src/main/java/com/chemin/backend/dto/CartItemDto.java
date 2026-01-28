package com.chemin.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {

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
