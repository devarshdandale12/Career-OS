package com.devarsh.careeros.service.impl;

import com.devarsh.careeros.dto.RegisterRequest;
import com.devarsh.careeros.dto.UserResponse;
import com.devarsh.careeros.entity.Role;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.exception.EmailAlreadyExistsException;
import com.devarsh.careeros.repository.UserRepository;
import com.devarsh.careeros.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        // 1. Clean the email: remove spaces and make it lowercase
        String email = request.email().trim().toLowerCase();

        // 2. Stop if this email already has an account
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("An account with this email already exists");
        }

        // 3. Build the new user
        User user = new User();
        user.setFullName(request.fullName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(true);

        // 4. Save it in the database
        User savedUser = userRepository.save(user);

        // 5. Send back only safe information
        return toResponse(savedUser);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}