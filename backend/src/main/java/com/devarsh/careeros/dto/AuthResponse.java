package com.devarsh.careeros.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        UserResponse user
) {
}