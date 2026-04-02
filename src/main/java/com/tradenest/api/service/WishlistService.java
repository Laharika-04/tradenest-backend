package com.tradenest.api.service;

import com.tradenest.api.dto.WishlistDto;
import com.tradenest.api.entity.*;
import com.tradenest.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<WishlistDto> getWishlist(Long userId) {

        return wishlistRepository.findByUserId(userId)
                .stream()
                .map(WishlistDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToWishlist(Long userId, Long productId) {

        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            return;
        }

        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist w = new Wishlist();
        w.setUser(user);
        w.setProduct(product);

        wishlistRepository.save(w);
    }

    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public boolean isWishlisted(Long userId, Long productId) {
        return wishlistRepository.existsByUserIdAndProductId(userId, productId);
    }
}