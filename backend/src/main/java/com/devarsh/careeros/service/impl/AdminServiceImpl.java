package com.devarsh.careeros.service.impl;

import com.devarsh.careeros.dto.AdminDashboardResponse;
import com.devarsh.careeros.dto.AdminUserResponse;
import com.devarsh.careeros.dto.PageResponse;
import com.devarsh.careeros.entity.Role;
import com.devarsh.careeros.entity.User;
import com.devarsh.careeros.exception.BadRequestException;
import com.devarsh.careeros.exception.ResourceNotFoundException;
import com.devarsh.careeros.repository.UserRepository;
import com.devarsh.careeros.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final int MAX_PAGE_SIZE = 50;

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AdminDashboardResponse getDashboard() {
        return new AdminDashboardResponse(
                userRepository.count(),
                userRepository.countByRole(Role.ADMIN),
                userRepository.countByRole(Role.USER),
                userRepository.countByEnabled(true),
                userRepository.countByEnabled(false),
                userRepository.countByCreatedAtAfter(LocalDateTime.now().minusDays(7))
        );
    }

    @Override
    public PageResponse<AdminUserResponse> getUsers(String keyword, int page, int size) {

        // 1. Keep the page number and page size inside safe limits
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        // 2. Ask for this page, newest accounts first
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by("createdAt").descending());

        // 3. With no search text, list everyone. Otherwise, search.
        Page<User> result;
        if (keyword == null || keyword.isBlank()) {
            result = userRepository.findAll(pageable);
        } else {
            result = userRepository.search(keyword.trim(), pageable);
        }

        // 4. Convert each User into an AdminUserResponse
        List<AdminUserResponse> content = new ArrayList<>();
        for (User user : result.getContent()) {
            content.add(toAdminResponse(user));
        }

        // 5. Wrap everything in our own PageResponse
        return new PageResponse<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public AdminUserResponse setUserEnabled(Long userId, boolean enabled, String currentAdminEmail) {

        // 1. Find the user, or stop with a "not found" error
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. An admin must not be able to lock themselves out
        if (!enabled && user.getEmail().equalsIgnoreCase(currentAdminEmail)) {
            throw new BadRequestException("You cannot disable your own account");
        }

        // 3. Change the flag and save
        user.setEnabled(enabled);
        User saved = userRepository.save(user);

        return toAdminResponse(saved);
    }

    private AdminUserResponse toAdminResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getLastLoginAt()
        );
    }
}