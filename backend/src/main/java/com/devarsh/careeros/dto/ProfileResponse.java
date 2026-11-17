package com.devarsh.careeros.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
        Long id,
        String fullName,
        String email,
        String headline,
        String targetRole,
        String phone,
        String location,
        String summary,
        String linkedinUrl,
        String githubUrl,
        String portfolioUrl,
        String profilePhotoUrl,
        LocalDateTime updatedAt
) {
}