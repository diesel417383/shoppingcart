package com.chemin.backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long addressId;
}
