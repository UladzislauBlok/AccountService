package com.Company.AccountService.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
