package com.api.ecommerce.dto;

import java.util.List;

public class PedidoRequest {
    private Long clienteId;
    private List<PedidoDetalleRequest> detalles;
    private Double total;  // Aquí agregamos el campo total

    // Getter y Setter para clienteId
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    // Getter y Setter para detalles
    public List<PedidoDetalleRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalleRequest> detalles) {
        this.detalles = detalles;
    }

    // Getter y Setter para total
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
