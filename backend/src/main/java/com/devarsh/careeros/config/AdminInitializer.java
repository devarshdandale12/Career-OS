package com.devarsh.careeros.config;

import com.devarsh.careeros.entity.Role;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;

    public AdminInitializer(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            @Value("${app.admin.name:Admin}") String adminName,
                            @Value("${app.admin.email:}") String adminEmail,
                            @Value("${app.admin.password:}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {

        // 1. If an admin already exists, there is nothing to do
        if (userRepository.countByRole(Role.ADMIN) > 0) {
            return;
        }

        // 2. Make sure the settings are there and the password is not too short
        if (adminEmail.isBlank() || adminPassword.length() < 8) {
            log.warn("No admin account was created. Set app.admin.email and "
                    + "app.admin.password (at least 8 characters).");
            return;
        }

        // 3. Make sure that email is not already used by a normal account
        String email = adminEmail.trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            log.warn("No admin account was created: the email {} is already registered "
                    + "as a normal user.", email);
            return;
        }

        // 4. Create the admin
        User admin = new User();
        admin.setFullName(adminName.trim());
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        userRepository.save(admin);

        log.info("First admin account created: {}", email);
    }
}