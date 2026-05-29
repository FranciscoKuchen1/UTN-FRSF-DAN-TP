package com.worldcupticket.msusers.entity;

/**
 * User role enumeration.
 *
 * Defines the available roles in the WorldCupTicket platform
 */
public enum UserRole {
    /**
     * Regular buyer role - can purchase tickets
     */
    BUYER("BUYER"),

    /**
     * Administrator role - full system access
     */
    ADMIN("ADMIN");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Get UserRole from string value
     *
     * @param value the string value
     * @return the corresponding UserRole
     * @throws IllegalArgumentException if the value is not valid
     */
    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}