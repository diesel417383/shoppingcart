package com.chemin.backend.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotNull
    private String account;

    @NotNull
    private String password;
}
