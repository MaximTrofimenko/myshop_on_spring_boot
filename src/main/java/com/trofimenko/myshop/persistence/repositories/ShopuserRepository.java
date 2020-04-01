package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Shopuser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShopuserRepository extends CrudRepository<Shopuser, UUID> {
    Shopuser findOneByPhone(String phone);
    boolean existsByPhone(String phone);
}
