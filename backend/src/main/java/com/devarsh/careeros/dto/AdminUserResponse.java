package com.devarsh.careeros.dto;

import com.devarsh.careeros.entity.Role;

import java.time.LocalDateTime;

public record AdminUserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt
) {
}