package com.api.ecommerce.controllers;

import com.api.ecommerce.dto.LoginClientRequest;
import com.api.ecommerce.dto.LoginRequest;
import com.api.ecommerce.dto.RegisterClientRequest;
import com.api.ecommerce.dto.RegisterRequest;
import com.api.ecommerce.entity.ClienteEntity;
import com.api.ecommerce.repository.ClienteRepository;
import com.api.ecommerce.repository.UsuarioRepository;
import com.api.ecommerce.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest)
    {
        Map<String, Object> response = new HashMap<>();

        Optional<UserEntity> usuario = usuarioRepository.findByUsername(loginRequest.getUsername());

        if (usuario.isPresent()) {
            UserEntity user = usuario.get();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                response.put("status", "success");
                response.put("role", user.getRole());
                response.put("username", user.getUsername());
            } else {
                response.put("status", "error");
                response.put("message", "Contraseña incorrecta");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Usuario no encontrado");
        }

        return response;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Optional<UserEntity> existingUser = usuarioRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // ⚠️ No encriptado
        user.setRole(request.getRole());
        user.setNombre(request.getNombre());
        usuarioRepository.save(user);

        return ResponseEntity.ok("Usuario registrado correctamente.");
    }
    @Autowired
    private ClienteRepository clienteRepository;
    // En AuthController
    @PostMapping("/login-client")
    public Map<String, Object> loginClient(@RequestBody LoginClientRequest loginClientRequest) {
        Map<String, Object> response = new HashMap<>();
        Optional<ClienteEntity> cliente = clienteRepository.findByEmail(loginClientRequest.getEmail());
        System.out.println(cliente.isPresent());
        if (cliente.isPresent()) {
            ClienteEntity user = cliente.get();
            if (user.getPassword().equals(loginClientRequest.getPassword())) {
                response.put("status", "success");
                response.put("username", user.getEmail());
                response.put("id", user.getId());
                // Otras propiedades que tengas
            } else {
                response.put("status", "error");
                response.put("message", "Contraseña incorrecta");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Usuario no encontrado");
        }
        return response;
    }
    @PostMapping("/register-client")
    public ResponseEntity<?> registerClient(@RequestBody RegisterClientRequest request) {
        Optional<ClienteEntity> existingClient = clienteRepository.findByEmail(request.getEmail());

        if (existingClient.isPresent()) {
            // Retornamos un objeto JSON con error en lugar de un string plano
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "El cliente ya existe.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        ClienteEntity nuevoCliente = new ClienteEntity();
        nuevoCliente.setNombre(request.getNombre());
        nuevoCliente.setEmail(request.getEmail());
        nuevoCliente.setPassword(request.getPassword()); // ⚠️ En producción, encripta
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setDireccion(request.getDireccion());

        clienteRepository.save(nuevoCliente);

        // Respuesta exitosa con un JSON
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("message", "Cliente registrado correctamente.");
        return ResponseEntity.ok(successResponse);
    }

}
