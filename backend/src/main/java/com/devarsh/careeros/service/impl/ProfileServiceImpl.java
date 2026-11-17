package com.devarsh.careeros.service.impl;

import com.devarsh.careeros.dto.ProfileRequest;
import com.devarsh.careeros.dto.ProfileResponse;
import com.devarsh.careeros.entity.Profile;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.repository.ProfileRepository;
import com.devarsh.careeros.repository.UserRepository;
import com.devarsh.careeros.security.CurrentUserService;
import com.devarsh.careeros.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              UserRepository userRepository,
                              CurrentUserService currentUserService) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public ProfileResponse getMyProfile() {
        User user = currentUserService.getCurrentUser();
        Profile profile = findOrCreateProfile(user);
        return toResponse(profile, user);
    }

    @Override
    @Transactional
    public ProfileResponse updateMyProfile(ProfileRequest request) {
        User user = currentUserService.getCurrentUser();
        Profile profile = findOrCreateProfile(user);

        // 1. The name belongs to the user table
        user.setFullName(request.fullName().trim());
        userRepository.save(user);

        // 2. The rest belongs to the profile table
        profile.setHeadline(clean(request.headline()));
        profile.setTargetRole(clean(request.targetRole()));
        profile.setPhone(clean(request.phone()));
        profile.setLocation(clean(request.location()));
        profile.setSummary(clean(request.summary()));
        profile.setLinkedinUrl(clean(request.linkedinUrl()));
        profile.setGithubUrl(clean(request.githubUrl()));
        profile.setPortfolioUrl(clean(request.portfolioUrl()));

        Profile saved = profileRepository.save(profile);

        return toResponse(saved, user);
    }

    // Finds this user's profile, or creates an empty one the first time
    private Profile findOrCreateProfile(User user) {
        return profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Profile newProfile = new Profile();
                    newProfile.setUser(user);
                    return profileRepository.save(newProfile);
                });
    }

    // Trims spaces, and turns empty text into null
    private String clean(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private ProfileResponse toResponse(Profile profile, User user) {
        return new ProfileResponse(
                profile.getId(),
                user.getFullName(),
                user.getEmail(),
                profile.getHeadline(),
                profile.getTargetRole(),
                profile.getPhone(),
                profile.getLocation(),
                profile.getSummary(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl(),
                profile.getPortfolioUrl(),
                profile.getProfilePhotoUrl(),
                profile.getUpdatedAt()
        );
    }
}