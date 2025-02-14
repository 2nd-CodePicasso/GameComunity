package com.example.codePicasso.domain.category.dto.request;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotBlank
        @Length(max = 10)
        String categoryName
) {
}
