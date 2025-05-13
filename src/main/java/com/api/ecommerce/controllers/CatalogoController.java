package com.api.ecommerce.controllers;

import com.api.ecommerce.entity.CatalogEntity;
import com.api.ecommerce.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

    @Autowired
    private CatalogoRepository catalogoRepository;

    @GetMapping
    public List<CatalogEntity> getAll() {
        return catalogoRepository.findAll();
    }

    @PostMapping
    public CatalogEntity create(@RequestBody CatalogEntity catalogo) {
        return catalogoRepository.save(catalogo);
    }

    @PutMapping("/{id}")
    public CatalogEntity update(@PathVariable Long id, @RequestBody CatalogEntity catalogo) {
        catalogo.setId(id);
        return catalogoRepository.save(catalogo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        catalogoRepository.deleteById(id);
    }
}
