package com.devarsh.careeros.controller;

import com.devarsh.careeros.dto.EducationRequest;
import com.devarsh.careeros.dto.EducationResponse;
import com.devarsh.careeros.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public List<EducationResponse> getMyEducations() {
        return educationService.getMyEducations();
    }

    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            @Valid @RequestBody EducationRequest request) {
        EducationResponse created = educationService.addEducation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public EducationResponse updateEducation(@PathVariable("id") Long id,
                                             @Valid @RequestBody EducationRequest request) {
        return educationService.updateEducation(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable("id") Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }
}