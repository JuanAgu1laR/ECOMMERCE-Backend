package com.api.ecommerce.repository;

import com.api.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductEntity, Long> {
}
