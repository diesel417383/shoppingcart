package com.chemin.backend.model.vo;

import com.chemin.backend.model.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String orderNo;

    @NotNull
    private AddressResponse address;

    @NotNull
    private BigDecimal totalAmount;

    @NotBlank
    private OrderStatusEnum status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private List<OrderItemResponse> orderItems;
}
