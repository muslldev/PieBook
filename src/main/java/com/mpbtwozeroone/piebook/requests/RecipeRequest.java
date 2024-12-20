package com.mpbtwozeroone.piebook.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String title;
    private String description;
    private int preparationTime;
    private int cookingTime;
    private Long categoryId;
}
