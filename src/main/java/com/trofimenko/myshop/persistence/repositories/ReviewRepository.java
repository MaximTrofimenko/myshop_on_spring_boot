package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Product;
import com.trofimenko.myshop.persistence.entities.Review;
import com.trofimenko.myshop.persistence.entities.Shopuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<List<Review>> findByProduct(Product product);
    Optional<List<Review>> findByShopuser(Shopuser shopuser);
}
