package com.tradenest.api.service;

import com.tradenest.api.entity.Category;
import com.tradenest.api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category getBySlug(String slug) {
        return repo.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

}