package com.trofimenko.myshop.persistence.entities;

import com.trofimenko.myshop.persistence.entities.utils.PersistableEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Purchase extends PersistableEntity {
    private Double price;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<CartRecord> cartRecords;

    @ManyToOne
    @JoinColumn(name = "shopuser")
    private Shopuser shopuser;

    @ManyToMany
    @JoinTable(name="purchase_product", joinColumns=
    @JoinColumn(name="purchase", referencedColumnName="id"), inverseJoinColumns=
    @JoinColumn(name="product", referencedColumnName="id"))
    private List<Product> products;
}
