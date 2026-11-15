package com.devarsh.careeros.service.impl;

import com.devarsh.careeros.dto.AuthResponse;
import com.devarsh.careeros.dto.LoginRequest;
import com.devarsh.careeros.dto.RegisterRequest;
import com.devarsh.careeros.dto.UserResponse;
import com.devarsh.careeros.entity.Role;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.exception.EmailAlreadyExistsException;
import com.devarsh.careeros.repository.UserRepository;
import com.devarsh.careeros.security.JwtService;
import com.devarsh.careeros.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1. Clean the email the same way as in registration
        String email = request.email().trim().toLowerCase();

        // 2. Let Spring Security check the email and password.
        //    If they are wrong, it throws an exception and we stop here.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.password()));

        // 3. Load the user and note the time of this login
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // 4. Create the token and send it back with basic user info
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer", toResponse(user));
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