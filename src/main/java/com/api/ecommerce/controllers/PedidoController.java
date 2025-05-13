package com.api.ecommerce.controllers;

import com.api.ecommerce.dto.*;
import com.api.ecommerce.entity.*;
import com.api.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDetalleRepository detalleRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productRepository;

    @Transactional  // Asegura que el proceso sea atómico
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) {
        // Verificar si el cliente existe
        Optional<ClienteEntity> clienteOpt = clienteRepository.findById(request.getClienteId());
        if (clienteOpt.isEmpty()) return ResponseEntity.badRequest().body("Cliente no encontrado");

        // Crear el pedido
        PedidoEntity pedido = new PedidoEntity();
        pedido.setCliente(clienteOpt.get());
        pedido.setFecha(new Date());
        pedido.setTotal(0.0); // Inicializamos el total como 0.0

        // Guardamos el pedido en la base de datos
        pedido = pedidoRepository.save(pedido);  // Al guardar el pedido, se genera un ID
        System.out.println("Pedido ID: " + pedido.getId());  // Verifica que el ID se asignó correctamente

        double total = 0.0; // Inicializamos el total a 0

        // Procesar los detalles del pedido
        for (PedidoDetalleRequest detReq : request.getDetalles()) {
            Optional<ProductEntity> prodOpt = productRepository.findById(detReq.getProductoId());
            if (prodOpt.isEmpty()) continue;  // Si el producto no existe, lo saltamos

            ProductEntity prod = prodOpt.get();

            // Crear el detalle del pedido
            PedidoDetalleEntity detalle = new PedidoDetalleEntity();
            detalle.setPedido(pedido);  // Relacionamos el detalle con el pedido
            detalle.setProducto(prod);  // Establecemos el producto
            detalle.setCantidad(detReq.getCantidad());  // Establecemos la cantidad
            detalle.setPrecioUnitario(prod.getPrecio());  // Establecemos el precio unitario

            // Guardar el detalle en la base de datos
            detalleRepository.save(detalle);

            // Calcular el total del pedido
            total += prod.getPrecio() * detReq.getCantidad();
        }

        // Actualizar el total del pedido
        pedido.setTotal(total);
        pedidoRepository.save(pedido);  // Guardamos el pedido con el total actualizado
        pedido = pedidoRepository.save(pedido);  // Esto debe asignar un id al pedido
        System.out.println("Pedido ID: " + pedido.getId());  // Verifica el id generado

        return ResponseEntity.ok(pedido);  // Retornamos el pedido con los detalles
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        // Asegurarse de que el método en el repositorio esté bien escrito
        List<PedidoEntity> pedidos = pedidoRepository.findByClienteId(clienteId);

        List<PedidoResponse> pedidosConDetalles = pedidos.stream().map(pedido -> {
            PedidoResponse dto = new PedidoResponse();
            dto.setId(pedido.getId());
            dto.setFecha(pedido.getFecha().toString());
            dto.setTotal(pedido.getTotal());

            // Cliente
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(pedido.getCliente().getId());
            clienteDTO.setNombre(pedido.getCliente().getNombre());
            clienteDTO.setEmail(pedido.getCliente().getEmail());
            clienteDTO.setTelefono(pedido.getCliente().getTelefono());
            clienteDTO.setDireccion(pedido.getCliente().getDireccion());
            dto.setCliente(clienteDTO);

            // Detalles
            List<PedidoDetalleResponse> detalles = pedido.getDetalles().stream().map(detalle -> {
                PedidoDetalleResponse detalleDTO = new PedidoDetalleResponse();
                detalleDTO.setNombre(detalle.getProducto().getNombre());
                detalleDTO.setPrecio(detalle.getPrecioUnitario()); // usamos precioUnitario
                detalleDTO.setCantidad(detalle.getCantidad());
                return detalleDTO;
            }).collect(Collectors.toList());

            dto.setDetalles(detalles);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pedidosConDetalles);
    }
    // Método para obtener todos los pedidos de todos los clientes
    @GetMapping("/admin/pedidos")
    public ResponseEntity<?> obtenerTodosLosPedidos() {
        // Obtenemos todos los pedidos
        List<PedidoEntity> pedidos = pedidoRepository.findAll();

        // Mapeamos los pedidos a DTOs con los detalles
        List<PedidoResponse> pedidosConDetalles = pedidos.stream().map(pedido -> {
            PedidoResponse dto = new PedidoResponse();
            dto.setId(pedido.getId());
            dto.setFecha(pedido.getFecha().toString());
            dto.setTotal(pedido.getTotal());

            // Cliente
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(pedido.getCliente().getId());
            clienteDTO.setNombre(pedido.getCliente().getNombre());
            clienteDTO.setEmail(pedido.getCliente().getEmail());
            clienteDTO.setTelefono(pedido.getCliente().getTelefono());
            clienteDTO.setDireccion(pedido.getCliente().getDireccion());
            dto.setCliente(clienteDTO);

            // Detalles
            List<PedidoDetalleResponse> detalles = pedido.getDetalles().stream().map(detalle -> {
                PedidoDetalleResponse detalleDTO = new PedidoDetalleResponse();
                detalleDTO.setNombre(detalle.getProducto().getNombre());
                detalleDTO.setPrecio(detalle.getPrecioUnitario()); // usamos precioUnitario
                detalleDTO.setCantidad(detalle.getCantidad());
                return detalleDTO;
            }).collect(Collectors.toList());

            dto.setDetalles(detalles);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pedidosConDetalles);
    }
}
