package com.trofimenko.myshop.services;

import com.trofimenko.myshop.persistence.entities.Purchase;
import com.trofimenko.myshop.persistence.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;


    public Purchase makePurchase(Purchase purchase){
        return purchaseRepository.save(purchase);
    }
}
