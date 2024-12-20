package com.mpbtwozeroone.piebook.repositories;

import com.mpbtwozeroone.piebook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
