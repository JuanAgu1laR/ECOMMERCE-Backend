package com.api.ecommerce.repository;

import com.api.ecommerce.entity.PedidoDetalleEntity;
import com.api.ecommerce.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalleEntity, Long> {
    List<PedidoDetalleEntity> findByPedido(PedidoEntity pedido);
    // Métodos adicionales si es necesario, como encontrar detalles de un pedido
}
