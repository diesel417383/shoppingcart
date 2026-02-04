package com.chemin.backend.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String account;

    @NotBlank
    private String role;

    @NotBlank
    private String token;
}
