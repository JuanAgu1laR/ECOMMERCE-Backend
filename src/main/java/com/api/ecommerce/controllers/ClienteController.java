package com.api.ecommerce.controllers;

import com.api.ecommerce.entity.ClienteEntity;
import com.api.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<ClienteEntity> getAll() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public ClienteEntity create(@RequestBody ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ClienteEntity update(@PathVariable Long id, @RequestBody ClienteEntity cliente) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
