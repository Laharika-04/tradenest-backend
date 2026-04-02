package com.tradenest.api.controller;

import com.tradenest.api.dto.CreateProductRequest;
import com.tradenest.api.dto.PagedResponse;
import com.tradenest.api.dto.ProductDto;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.ProductService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ── Public list (legacy, un-paginated) ────────────────────────────────────
    @GetMapping
    public ApiResponse<List<ProductDto>> all() {
        return ApiResponse.ok(service.getAll());
    }

    // ── Paginated product list ────────────────────────────────────────────────
    @GetMapping("/paged")
    public ApiResponse<PagedResponse<ProductDto>> allPaged(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.ok(service.getAllPaged(page, size));
    }

    // ── Backend search ────────────────────────────────────────────────────────
    @GetMapping("/search")
    public ApiResponse<PagedResponse<ProductDto>> search(
            @RequestParam(required = false)    String     q,
            @RequestParam(required = false)    String     city,
            @RequestParam(required = false)    String     category,
            @RequestParam(required = false)    BigDecimal minPrice,
            @RequestParam(required = false)    BigDecimal maxPrice,
            @RequestParam(defaultValue = "0")  int        page,
            @RequestParam(defaultValue = "20") int        size) {

        return ApiResponse.ok(
                service.search(q, city, category, minPrice, maxPrice, page, size)
        );
    }

    // ── Products by category (paginated) ─────────────────────────────────────
    // NOTE: /featured and /my must stay ABOVE /{id} so Spring doesn't treat them as Long
    @GetMapping("/category/{slug}")
    public ApiResponse<PagedResponse<ProductDto>> byCategory(
            @PathVariable                      String slug,
            @RequestParam(defaultValue = "0")  int    page,
            @RequestParam(defaultValue = "20") int    size) {
        return ApiResponse.ok(service.getByCategory(slug, page, size));
    }

    @GetMapping("/featured")
    public ApiResponse<List<ProductDto>> featured() {
        return ApiResponse.ok(service.getFeaturedProducts());
    }

    @GetMapping("/my")
    public ApiResponse<List<ProductDto>> myProducts(@AuthenticationPrincipal User user) {
        if (user == null) throw new RuntimeException("Unauthorized");
        return ApiResponse.ok(service.getBySeller(user.getId()));
    }

    @GetMapping("/seller/{userId}")
    public ApiResponse<List<ProductDto>> bySeller(@PathVariable Long userId) {
        return ApiResponse.ok(service.getBySeller(userId));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDto> one(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    // FIX: Accept DTO instead of raw entity — prevents client from injecting
    //      sellerId, status, viewCount, postedDate, etc.
    @PostMapping
    public ApiResponse<ProductDto> create(
            @AuthenticationPrincipal User user,
            @RequestBody CreateProductRequest req) {

        if (user == null) throw new RuntimeException("Unauthorized");
        return ApiResponse.ok(service.create(req, user));
    }

    // FIX: Same fix for update
    @PutMapping("/{id}")
    public ApiResponse<ProductDto> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody CreateProductRequest req) {

        if (user == null) throw new RuntimeException("Unauthorized");
        return ApiResponse.ok(service.update(id, user.getId(), req));
    }

    @PutMapping("/{id}/sold")
    public ApiResponse<ProductDto> markAsSold(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        if (user == null) throw new RuntimeException("Unauthorized");
        return ApiResponse.ok(service.markAsSold(id, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        if (user == null) throw new RuntimeException("Unauthorized");
        service.delete(id, user.getId());
        return ApiResponse.ok(null, "Deleted");
    }
}