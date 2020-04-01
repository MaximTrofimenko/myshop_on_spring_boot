package com.trofimenko.myshop.services;

import com.trofimenko.myshop.exceptions.ProductNotFoundException;
import com.trofimenko.myshop.persistence.entities.Image;
import com.trofimenko.myshop.persistence.entities.Product;
import com.trofimenko.myshop.persistence.entities.enums.ProductCategory;
import com.trofimenko.myshop.persistence.pojo.ProductPojo;
import com.trofimenko.myshop.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.ProviderNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
                () -> new ProviderNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

    public List<Product> findAll(Integer category){
        return category == null ?
                productRepository.findAll() : productRepository.findAllByCategory(ProductCategory.values()[category]);
    }

    @Transactional
    public String save(ProductPojo productPogo, Image image) {

        Product product = Product.builder()
                .added(new Date())
                .title(productPogo.getTitle())
                .description(productPogo.getDescription())
                .price(productPogo.getPrice())
                .available(productPogo.isAvailable())
                .category(productPogo.getCategory())
                .image(image)
                .build();

        productRepository.save(product);
        log.info("New Product has been succesfully added! {}", product);
        return "redirect:/";
    }
}
