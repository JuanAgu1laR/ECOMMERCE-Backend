package com.api.ecommerce.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    private String nombre;
    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public String getNombre() {
            return nombre;
    }
    public void setNombre(String nombre) {
            this.nombre = nombre;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
