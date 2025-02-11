package com.example.codePicasso.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SigninRequest (@NotBlank
                             String loginId,

                             @NotBlank
                             String password){

}
