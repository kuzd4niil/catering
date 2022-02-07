package com.example.demo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    GUEST(Sets.newHashSet(READ_CATERING))
    ,USER(Sets.newHashSet(READ_CATERING, MAKE_AN_ORDER))
    ,OWNER(Sets.newHashSet(READ_CATERING, MAKE_AN_ORDER, MODIFY_CATERING))
    ,ADMIN(Sets.newHashSet(READ_CATERING, READ_USER, MAKE_AN_ORDER, MODIFY_CATERING, MODIFY_USER));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }

    static public ApplicationUserRole stringToRole(String roleName) {
        ApplicationUserRole applicationUserRole;
        switch (roleName) {
            case ("GUEST"):
                applicationUserRole = GUEST;
                break;
            case ("USER"):
                applicationUserRole = USER;
                break;
            case ("OWNER"):
                applicationUserRole = OWNER;
                break;
            default:
                applicationUserRole = ADMIN;
        }

        return applicationUserRole;
    }
}
