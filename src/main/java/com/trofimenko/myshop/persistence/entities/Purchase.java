package com.trofimenko.myshop.persistence.entities;

import com.trofimenko.myshop.persistence.entities.utils.PersistableEntity;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Purchase extends PersistableEntity {
    private Double price;
    private String address;
    private String phone;
}
