package com.api.ecommerce.repository;

import com.api.ecommerce.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    // Método correcto para encontrar los pedidos por el ID del cliente
    List<PedidoEntity> findByClienteId(Long clienteId);
}
