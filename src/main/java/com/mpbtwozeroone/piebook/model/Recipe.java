package com.mpbtwozeroone.piebook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipe_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int preparationTime;

    @Column(nullable = false)
    private int cookingTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Integer getId() {
        return recipe_id;
    }

    public void setId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }
}
