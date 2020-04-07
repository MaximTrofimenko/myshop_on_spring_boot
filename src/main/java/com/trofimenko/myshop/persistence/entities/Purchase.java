package com.trofimenko.myshop.persistence.entities;

import com.trofimenko.myshop.persistence.entities.utils.PersistableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Purchase extends PersistableEntity {
    private Double price;
    private String address;
    private String phone;
}
