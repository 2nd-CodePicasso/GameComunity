package com.example.codePicasso.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest (@NotBlank
                             String loginId,

                             @NotBlank
                             String password){

}
