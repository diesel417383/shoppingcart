package com.chemin.backend.dto.response;

import com.chemin.backend.dto.CartItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CartItemResponse {

    @NotNull
    private List<CartItemDto> items;

    @NotNull
    private Double totalPrice;
}
