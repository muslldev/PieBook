package com.mpbtwozeroone.piebook.services;

import com.mpbtwozeroone.piebook.model.FileStorage;
import com.mpbtwozeroone.piebook.model.Recipe;
import com.mpbtwozeroone.piebook.repositories.CategoryRepository;
import com.mpbtwozeroone.piebook.repositories.RecipeRepository;
import com.mpbtwozeroone.piebook.requests.RecipeRequest;
import com.mpbtwozeroone.piebook.responses.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById((long) Math.toIntExact(id));
    }

    public void createRecipe(RecipeRequest recipeRequest) {
        var category = categoryRepository.findById(recipeRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        FileUploadResponse uploadResponse = fileStorageService.generateUploadUrl(
                "recipes",
                "temp",
              recipeRequest.getImageFile().getOriginalFilename(),
                recipeRequest.getImageFile().getContentType()
        );

        fileStorageService.uploadFile(uploadResponse.getUploadUrl(), recipeRequest.getImageFile());

        var recipe = Recipe.builder()
                .title(recipeRequest.getTitle())
                .description(recipeRequest.getDescription())
                .preparationTime(recipeRequest.getPreparationTime())
                .cookingTime(recipeRequest.getCookingTime())
                .category(category)
                .build();

        Recipe savedRecipe = recipeRepository.save(recipe);

        FileStorage fileRecord = uploadResponse.getFileRecord();
        fileRecord.setExternalId(savedRecipe.getId().toString());
        fileStorageService.updateFileRecord(fileRecord);

        savedRecipe.setImageFile(fileRecord);
        recipeRepository.save(savedRecipe);
    }

    public Optional<Recipe> updateRecipe(Long id, RecipeRequest recipeRequest) {
        var category = categoryRepository.findById(recipeRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return recipeRepository.findById((long) Math.toIntExact(id)).map(existingRecipe -> {
            existingRecipe.setTitle(recipeRequest.getTitle());
            existingRecipe.setDescription(recipeRequest.getDescription());
            existingRecipe.setPreparationTime(recipeRequest.getPreparationTime());
            existingRecipe.setCookingTime(recipeRequest.getCookingTime());
            existingRecipe.setCategory(category);
            recipeRepository.save(existingRecipe);
            return existingRecipe;
        });
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById((long) Math.toIntExact(id));
    }
}
