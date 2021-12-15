package com.example.demo.security;

public enum ApplicationUserPermission {
    READ_CATERING("READ_CATERING"),
    READ_USER("READ_USER"),
    MAKE_AN_ORDER("MAKE_AN_ORDER"),
    MODIFY_CATERING("MODIFY_CATERING"),
    MODIFY_USER("MODIFY_USER");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
