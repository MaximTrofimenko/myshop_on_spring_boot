package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
