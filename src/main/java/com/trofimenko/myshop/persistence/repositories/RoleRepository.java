package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findOneByName(String name);
}
