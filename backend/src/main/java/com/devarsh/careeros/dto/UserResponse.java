package com.devarsh.careeros.dto;

import com.devarsh.careeros.entity.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        LocalDateTime createdAt
) {
}