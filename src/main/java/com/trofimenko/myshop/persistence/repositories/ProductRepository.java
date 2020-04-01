package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Product;
import com.trofimenko.myshop.persistence.entities.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();
    List<Product> findAllByCategory(ProductCategory category);
}
