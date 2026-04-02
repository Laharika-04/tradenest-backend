package com.tradenest.api.service;

import com.tradenest.api.dto.OfferDto;
import com.tradenest.api.entity.*;
import com.tradenest.api.enums.OfferStatus;
import com.tradenest.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;

    public OfferService(OfferRepository offerRepository,
                        ProductRepository productRepository) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OfferDto makeOffer(User buyer, Long productId,
                              BigDecimal amount, String message) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getSeller().getId().equals(buyer.getId())) {
            throw new RuntimeException("Cannot offer on your own product");
        }

        Offer offer = new Offer();
        offer.setBuyer(buyer);
        offer.setProduct(product);
        offer.setAmount(amount);
        offer.setMessage(message);

        return OfferDto.fromEntity(offerRepository.save(offer));
    }

    public List<OfferDto> getMyOffers(Long userId) {
        return offerRepository.findByBuyerIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(OfferDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<OfferDto> getReceivedOffers(Long userId) {
        return offerRepository.findByProductSellerIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(OfferDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public OfferDto accept(Long offerId, Long sellerId) {

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        validateSeller(offer, sellerId);

        offer.setStatus(OfferStatus.ACCEPTED);

        return OfferDto.fromEntity(offerRepository.save(offer));
    }

    @Transactional
    public OfferDto reject(Long offerId, Long sellerId) {

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        validateSeller(offer, sellerId);

        offer.setStatus(OfferStatus.REJECTED);

        return OfferDto.fromEntity(offerRepository.save(offer));
    }

    @Transactional
    public OfferDto counter(Long offerId, Long sellerId, BigDecimal amount) {

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        validateSeller(offer, sellerId);

        offer.setStatus(OfferStatus.COUNTERED);
        offer.setCounterAmount(amount);

        return OfferDto.fromEntity(offerRepository.save(offer));
    }

    private void validateSeller(Offer offer, Long sellerId) {
        if (!offer.getProduct().getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("Unauthorized action");
        }
    }
}