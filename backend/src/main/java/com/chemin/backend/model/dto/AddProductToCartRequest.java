package com.chemin.backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductToCartRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
