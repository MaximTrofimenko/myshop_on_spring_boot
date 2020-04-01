package com.trofimenko.myshop.persistence.repositories;

import com.trofimenko.myshop.persistence.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    //метод возвращает имя картинки по id продкта, путем запроса объединения таблиц
    @Query(value = "SELECT image.name FROM image INNER JOIN product p ON image.id = p.image WHERE p.id = :id", nativeQuery = true)
    String obtainImageNameByProductId(@Param("id") UUID id);
}