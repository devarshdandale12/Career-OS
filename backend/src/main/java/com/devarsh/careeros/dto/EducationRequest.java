package com.devarsh.careeros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EducationRequest(

        @NotBlank(message = "Institution is required")
        @Size(max = 150, message = "Institution must be at most 150 characters")
        String institution,

        @NotBlank(message = "Degree is required")
        @Size(max = 100, message = "Degree must be at most 100 characters")
        String degree,

        @Size(max = 100, message = "Field of study must be at most 100 characters")
        String fieldOfStudy,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        LocalDate endDate,

        @Size(max = 30, message = "Marks must be at most 30 characters")
        String marks
) {
}