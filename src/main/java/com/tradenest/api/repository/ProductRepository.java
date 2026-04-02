package com.tradenest.api.repository;

import com.tradenest.api.entity.Product;
import com.tradenest.api.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ── Existing queries ───────────────────────────────────────────────────────

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
           "WHERE p.status = :status ORDER BY p.postedDate DESC")
    List<Product> findByStatus(@Param("status") ProductStatus status);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
           "WHERE p.seller.id = :sellerId ORDER BY p.postedDate DESC")
    List<Product> findBySellerId(@Param("sellerId") Long sellerId);

    // FIX: replaces findBySellerId().size() in DashboardService
    @Query("SELECT COUNT(p) FROM Product p WHERE p.seller.id = :sellerId")
    int countBySellerId(@Param("sellerId") Long sellerId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
           "WHERE p.featured = true AND p.status = :status ORDER BY p.postedDate DESC")
    List<Product> findByFeaturedTrueAndStatus(@Param("status") ProductStatus status);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
           "WHERE p.id = :id")
    Optional<Product> findByIdWithDetails(@Param("id") Long id);

    // ── Category filter with pagination ───────────────────────────────────────

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
                   "WHERE p.category.slug = :slug AND p.status = :status ORDER BY p.postedDate DESC",
           countQuery = "SELECT COUNT(p) FROM Product p " +
                        "WHERE p.category.slug = :slug AND p.status = :status")
    Page<Product> findByCategorySlugAndStatusPageable(@Param("slug")   String slug,
                                                      @Param("status") ProductStatus status,
                                                      Pageable pageable);

    // ── Full-text search with optional filters ────────────────────────────────

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
                   "WHERE p.status = :status " +
                   "AND (:q IS NULL OR LOWER(p.title) LIKE :q OR LOWER(p.description) LIKE :q) " +
                   "AND (:city IS NULL OR LOWER(p.city) = :city) " +
                   "AND (:slug IS NULL OR p.category.slug = :slug) " +
                   "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                   "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
                   "ORDER BY p.postedDate DESC",
           countQuery = "SELECT COUNT(p) FROM Product p " +
                        "WHERE p.status = :status " +
                        "AND (:q IS NULL OR LOWER(p.title) LIKE :q OR LOWER(p.description) LIKE :q) " +
                        "AND (:city IS NULL OR LOWER(p.city) = :city) " +
                        "AND (:slug IS NULL OR p.category.slug = :slug) " +
                        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                        "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> search(@Param("q")        String q,
                         @Param("city")     String city,
                         @Param("slug")     String slug,
                         @Param("minPrice") BigDecimal minPrice,
                         @Param("maxPrice") BigDecimal maxPrice,
                         @Param("status")   ProductStatus status,
                         Pageable pageable);

    // ── Paginated all-active listing ──────────────────────────────────────────

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.seller " +
                   "WHERE p.status = :status ORDER BY p.postedDate DESC",
           countQuery = "SELECT COUNT(p) FROM Product p WHERE p.status = :status")
    Page<Product> findByStatusPageable(@Param("status") ProductStatus status, Pageable pageable);
}