package com.api.ecommerce.dto;

import java.util.List;

public class PedidoResponse {
    private Long id;
    private ClienteDTO cliente;
    private String fecha;
    private Double total;
    private List<PedidoDetalleResponse> detalles;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<PedidoDetalleResponse> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalleResponse> detalles) {
        this.detalles = detalles;
    }
}
