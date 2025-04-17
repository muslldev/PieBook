package com.mpbtwozeroone.piebook.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Positive
    private int preparationTime;

    @Positive
    private int cookingTime;

    @NotNull
    private Long categoryId;

    @NotNull
    private MultipartFile imageFile;
}
