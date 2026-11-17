package com.devarsh.careeros.controller;

import com.devarsh.careeros.dto.ProfileRequest;
import com.devarsh.careeros.dto.ProfileResponse;
import com.devarsh.careeros.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ProfileResponse getMyProfile() {
        return profileService.getMyProfile();
    }

    @PutMapping
    public ProfileResponse updateMyProfile(@Valid @RequestBody ProfileRequest request) {
        return profileService.updateMyProfile(request);
    }
}