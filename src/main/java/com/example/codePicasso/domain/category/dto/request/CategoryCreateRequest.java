package com.example.codePicasso.domain.category.dto.request;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryCreateRequest (
        @NotBlank
        @Length(max = 10)
        String categoryName
) {
}
