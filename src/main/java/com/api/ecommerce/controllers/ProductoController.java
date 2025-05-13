package com.api.ecommerce.controllers;

import com.api.ecommerce.ProductService;
import com.api.ecommerce.entity.CatalogEntity;
import com.api.ecommerce.entity.ProductEntity;
import com.api.ecommerce.repository.CatalogoRepository;
import com.api.ecommerce.repository.CatalogoRepository;
import com.api.ecommerce.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductService productService;
    private final CatalogoRepository catalogRepository;

    public ProductoController(ProductService productService, CatalogoRepository catalogRepository) {
        this.productService = productService;
        this.catalogRepository = catalogRepository;
    }

    @GetMapping
    public List<ProductEntity> listar() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> obtener(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductEntity product) {
        if (product.getCatalogo() != null && product.getCatalogo().getId() != null) {
            Optional<CatalogEntity> catalog = catalogRepository.findById(product.getCatalogo().getId());
            if (catalog.isPresent()) {
                product.setCatalogo(catalog.get());
                return ResponseEntity.ok(productService.save(product));
            } else {
                return ResponseEntity.badRequest().body("Catálogo no encontrado.");
            }
        } else {
            return ResponseEntity.badRequest().body("Catálogo requerido.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductEntity updatedProduct) {
        return productService.findById(id).map(product -> {
            product.setNombre(updatedProduct.getNombre());
            product.setPrecio(updatedProduct.getPrecio());
            product.setImagenUrl(updatedProduct.getImagenUrl());

            if (updatedProduct.getCatalogo() != null && updatedProduct.getCatalogo().getId() != null) {
                Optional<CatalogEntity> catalog = catalogRepository.findById(updatedProduct.getCatalogo().getId());
                catalog.ifPresent(product::setCatalogo);
            }

            return ResponseEntity.ok(productService.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
