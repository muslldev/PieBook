package com.mpbtwozeroone.piebook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;

    @Column(nullable = false, unique = true)
    private String type;

    public Integer getId() {
        return category_id;
    }

    public void setId(Integer category_id) {
        this.category_id = category_id;
    }
}
