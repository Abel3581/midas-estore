package com.midas.store.repository;

import com.midas.store.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByName(String name);

    ProductEntity findByNameAndDescription(String name, String description);
}
