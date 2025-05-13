package com.api.ecommerce.repository;

import com.api.ecommerce.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogoRepository extends JpaRepository<CatalogEntity, Long> {
}
