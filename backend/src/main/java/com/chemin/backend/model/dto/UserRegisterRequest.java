package com.chemin.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank
    private String account;

    @NotBlank
    @Size(min = 5)
    private String password;

    @Email
    @NotBlank
    private String email;

}
