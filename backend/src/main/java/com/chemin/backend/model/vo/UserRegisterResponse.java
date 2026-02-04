package com.chemin.backend.model.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterResponse {

    @NotBlank
    private String account;

    @Email
    @NotBlank
    private String email;
}
