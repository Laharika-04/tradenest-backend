package com.tradenest.api.repository;

import com.tradenest.api.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);

    List<Offer> findByProductSellerIdOrderByCreatedAtDesc(Long sellerId);

    int countByProductSellerId(Long sellerId);
}