package com.chemin.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String phone;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String detailAddress;

    @NotNull
    private Boolean isDefault;

}
