package com.worldcupticket.msusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Aplicación principal del microservicio de Usuarios (Autenticación, Roles y Perfil).
 * 
 * WorldCupTicket - Microservicio de Usuarios
 * Responsabilidades:
 * - Autenticación de usuarios
 * - Gestión de roles y permisos
 * - Perfil de usuario
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MsUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }

}