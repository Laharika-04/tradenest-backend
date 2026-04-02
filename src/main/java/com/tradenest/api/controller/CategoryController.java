package com.tradenest.api.controller;

import com.tradenest.api.entity.Category;
import com.tradenest.api.service.CategoryService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Category>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @GetMapping("/{slug}")
    public ApiResponse<Category> getBySlug(@PathVariable String slug) {
        return ApiResponse.ok(service.getBySlug(slug));
    }

}