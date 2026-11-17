package com.devarsh.careeros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileRequest(

        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String fullName,

        @Size(max = 150, message = "Headline must be at most 150 characters")
        String headline,

        @Size(max = 100, message = "Target role must be at most 100 characters")
        String targetRole,

        @Pattern(regexp = "^$|^[+0-9 ()-]{7,20}$", message = "Enter a valid phone number")
        String phone,

        @Size(max = 100, message = "Location must be at most 100 characters")
        String location,

        @Size(max = 1000, message = "Summary must be at most 1000 characters")
        String summary,

        @Size(max = 255, message = "LinkedIn link is too long")
        @Pattern(regexp = "^$|^https?://.+", message = "LinkedIn link must start with http:// or https://")
        String linkedinUrl,

        @Size(max = 255, message = "GitHub link is too long")
        @Pattern(regexp = "^$|^https?://.+", message = "GitHub link must start with http:// or https://")
        String githubUrl,

        @Size(max = 255, message = "Portfolio link is too long")
        @Pattern(regexp = "^$|^https?://.+", message = "Portfolio link must start with http:// or https://")
        String portfolioUrl
) {
}