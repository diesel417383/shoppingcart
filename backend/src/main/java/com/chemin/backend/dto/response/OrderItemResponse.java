package com.chemin.backend.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer quantity;
}
