package com.trofimenko.myshop.services;

import com.trofimenko.myshop.persistence.entities.Product;
import com.trofimenko.myshop.persistence.entities.Review;
import com.trofimenko.myshop.persistence.entities.Shopuser;
import com.trofimenko.myshop.persistence.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product){
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopuser(Shopuser shopuser){
        return reviewRepository.findByShopuser(shopuser);
    }

    public void save(Review review){
        reviewRepository.save(review);
    }

    public UUID moderate(UUID id, String option) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Oops! Review not found!")
        );

        review.setApproved(option.equals("approve"));
        save(review);
        return review.getProduct().getId();
    }
}
