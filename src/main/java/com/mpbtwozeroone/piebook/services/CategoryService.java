package com.mpbtwozeroone.piebook.services;

import com.mpbtwozeroone.piebook.model.Category;
import com.mpbtwozeroone.piebook.repositories.CategoryRepository;
import com.mpbtwozeroone.piebook.requests.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById((long) Math.toIntExact(id));
    }

    public void createCategory(CategoryRequest categoryRequest) {
        var category = Category.builder()
                .type(categoryRequest.getType())
                .build();
        categoryRepository.save(category);
    }


    public Optional<Category> updateCategory(Long id, CategoryRequest categoryRequest) {
        return categoryRepository.findById((long) Math.toIntExact(id)).map(existingCategory -> {
            existingCategory.setType(categoryRequest.getType());
            categoryRepository.save(existingCategory);
            return existingCategory;
        });
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById((long) Math.toIntExact(id));
    }
}
