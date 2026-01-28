package com.chemin.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    FINISHED,
    CANCELLED;
}
