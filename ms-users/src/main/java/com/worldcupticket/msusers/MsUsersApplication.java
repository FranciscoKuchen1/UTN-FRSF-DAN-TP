package com.worldcupticket.msusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Users Microservice (Authentication, Roles and Profile).
 *
 * WorldCupTicket - Users Microservice
 * Responsibilities:
 * - User authentication
 * - Role and permission management
 * - User profile management
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MsUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsersApplication.class, args);
    }

}