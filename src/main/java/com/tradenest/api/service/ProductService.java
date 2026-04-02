package com.tradenest.api.service;

import com.tradenest.api.dto.CreateProductRequest;
import com.tradenest.api.dto.PagedResponse;
import com.tradenest.api.dto.ProductDto;
import com.tradenest.api.entity.Category;
import com.tradenest.api.entity.Product;
import com.tradenest.api.entity.User;
import com.tradenest.api.enums.ProductStatus;
import com.tradenest.api.repository.CategoryRepository;
import com.tradenest.api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository  repo;
    private final CategoryRepository categoryRepo;

    public ProductService(ProductRepository repo, CategoryRepository categoryRepo) {
        this.repo         = repo;
        this.categoryRepo = categoryRepo;
    }

    // ── Existing: un-paginated list (kept for backward compat) ───────────────
    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        return repo.findByStatus(ProductStatus.ACTIVE)
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ── Paginated all-active ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<ProductDto> getAllPaged(int page, int size) {
        Pageable pg = PageRequest.of(page, size, Sort.by("postedDate").descending());
        Page<ProductDto> result = repo.findByStatusPageable(ProductStatus.ACTIVE, pg)
                .map(ProductDto::fromEntity);
        return PagedResponse.of(result);
    }

    // ── Backend search ────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<ProductDto> search(String q,
                                            String city,
                                            String categorySlug,
                                            BigDecimal minPrice,
                                            BigDecimal maxPrice,
                                            int page,
                                            int size) {

        String likeQ     = (q != null && !q.isBlank())
                ? "%" + q.toLowerCase().trim() + "%" : null;
        String cityParam = (city != null && !city.isBlank())
                ? city.toLowerCase().trim() : null;
        String slugParam = (categorySlug != null && !categorySlug.isBlank())
                ? categorySlug.trim() : null;

        Pageable pg = PageRequest.of(page, size, Sort.by("postedDate").descending());
        Page<ProductDto> result = repo.search(
                likeQ, cityParam, slugParam, minPrice, maxPrice,
                ProductStatus.ACTIVE, pg
        ).map(ProductDto::fromEntity);

        return PagedResponse.of(result);
    }

    // ── Products by category slug (paginated) ─────────────────────────────────
    @Transactional(readOnly = true)
    public PagedResponse<ProductDto> getByCategory(String slug, int page, int size) {
        Pageable pg = PageRequest.of(page, size, Sort.by("postedDate").descending());
        Page<ProductDto> result = repo.findByCategorySlugAndStatusPageable(
                slug, ProductStatus.ACTIVE, pg
        ).map(ProductDto::fromEntity);
        return PagedResponse.of(result);
    }

    @Transactional
    public ProductDto getById(Long id) {
        Product p = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setViewCount(p.getViewCount() + 1);
        return ProductDto.fromEntity(repo.save(p));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getFeaturedProducts() {
        return repo.findByFeaturedTrueAndStatus(ProductStatus.ACTIVE)
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getBySeller(Long sellerId) {
        return repo.findBySellerId(sellerId)
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    // FIX: Accept DTO instead of raw entity — resolves category from ID safely
    @Transactional
    public ProductDto create(CreateProductRequest req, User seller) {
        Category category = resolveCategory(req.getCategory());

        Product p = new Product();
        p.setSeller(seller);
        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setCity(req.getCity());
        p.setState(req.getState());
        p.setImages(req.getImages() != null ? req.getImages() : "sample.jpg");
        p.setFeatured(req.isFeatured());
        p.setCondition(req.getCondition());   // FIX: was never set from DTO before
        p.setCategory(category);
        p.setStatus(ProductStatus.ACTIVE);

        Product saved = repo.save(p);
        return ProductDto.fromEntity(
                repo.findByIdWithDetails(saved.getId()).orElse(saved)
        );
    }

    // FIX: Accept DTO instead of raw entity + fix: condition is now updated
    @Transactional
    public ProductDto update(Long id, Long sellerId, CreateProductRequest req) {
        Product existing = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existing.getSeller().getId().equals(sellerId))
            throw new RuntimeException("Unauthorized");

        if (req.getTitle()       != null) existing.setTitle(req.getTitle());
        if (req.getDescription() != null) existing.setDescription(req.getDescription());
        if (req.getPrice()       != null) existing.setPrice(req.getPrice());
        if (req.getCity()        != null) existing.setCity(req.getCity());
        if (req.getState()       != null) existing.setState(req.getState());
        if (req.getImages()      != null) existing.setImages(req.getImages());
        if (req.getCondition()   != null) existing.setCondition(req.getCondition()); // FIX
        existing.setFeatured(req.isFeatured());

        if (req.getCategory() != null && req.getCategory().getId() != null) {
            existing.setCategory(resolveCategory(req.getCategory()));
        }

        return ProductDto.fromEntity(repo.save(existing));
    }

    // ── Mark as sold ──────────────────────────────────────────────────────────
    @Transactional
    public ProductDto markAsSold(Long id, Long sellerId) {
        Product existing = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existing.getSeller().getId().equals(sellerId))
            throw new RuntimeException("Unauthorized");

        existing.setStatus(ProductStatus.SOLD);
        return ProductDto.fromEntity(repo.save(existing));
    }

    // Soft-delete (EXPIRED)
    @Transactional
    public void delete(Long id, Long sellerId) {
        Product existing = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existing.getSeller().getId().equals(sellerId))
            throw new RuntimeException("Unauthorized");

        existing.setStatus(ProductStatus.EXPIRED);
        repo.save(existing);
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private Category resolveCategory(CreateProductRequest.CategoryRef ref) {
        if (ref == null || ref.getId() == null)
            throw new RuntimeException("Category is required");
        return categoryRepo.findById(ref.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}