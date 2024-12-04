package com.mpbtwozeroone.piebook.repositories;

import com.mpbtwozeroone.piebook.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
