package com.devarsh.careeros.security;

import com.devarsh.careeros.entity.Profile;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.exception.ResourceNotFoundException;
import com.devarsh.careeros.repository.ProfileRepository;
import com.devarsh.careeros.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public CurrentUserService(UserRepository userRepository,
                              ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // The current user's profile (created empty if they don't have one yet)
    public Profile getCurrentProfile() {
        User user = getCurrentUser();

        return profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Profile newProfile = new Profile();
                    newProfile.setUser(user);
                    return profileRepository.save(newProfile);
                });
    }
}