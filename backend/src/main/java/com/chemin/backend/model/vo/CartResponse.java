package com.chemin.backend.model.vo;

import com.chemin.backend.model.dto.CartItemResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CartResponse {

    @NotNull
    private List<CartItemResponse> items;

    @NotNull
    private Double totalPrice;
}
