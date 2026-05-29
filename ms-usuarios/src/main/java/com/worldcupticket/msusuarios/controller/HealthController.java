package com.worldcupticket.msusuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de salud de la aplicación.
 * 
 * Proporciona endpoints básicos para verificar el estado del servicio
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<?> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "ms-usuarios");
        response.put("message", "Microservicio de Usuarios - Activo");
        return ResponseEntity.ok(response);
    }

}