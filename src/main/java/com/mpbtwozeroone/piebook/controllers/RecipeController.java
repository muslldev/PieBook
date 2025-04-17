package com.mpbtwozeroone.piebook.controllers;

import com.mpbtwozeroone.piebook.model.Recipe;
import com.mpbtwozeroone.piebook.requests.RecipeRequest;
import com.mpbtwozeroone.piebook.services.RecipeService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    @Autowired
    private final RecipeService recipeService;
    private final MeterRegistry meterRegistry;

    @GetMapping
    public ResponseEntity<Page<Recipe>> getAllRecipes(Pageable pageable) {
        meterRegistry.counter("recipes_count", List.of()).increment();
        return ResponseEntity.ok(recipeService.getAllRecipes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createRecipe(
            @Valid @ModelAttribute RecipeRequest recipeRequest
            ) {
        recipeService.createRecipe(recipeRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Recipe>> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeRequest recipeRequest
    ) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
