package com.trofimenko.myshop.persistence.pojo;

import com.trofimenko.myshop.persistence.entities.enums.ProductCategory;
import lombok.Data;

@Data
public class ProductPojo {
    private String title;
    private String description;
    private Double price;
    private boolean available;
    private ProductCategory category;
}