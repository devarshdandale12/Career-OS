package com.devarsh.careeros.controller;

import com.devarsh.careeros.dto.AdminDashboardResponse;
import com.devarsh.careeros.dto.AdminUserResponse;
import com.devarsh.careeros.dto.PageResponse;
import com.devarsh.careeros.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/ping")
    public String adminPing(Authentication authentication) {
        return "Hello admin " + authentication.getName() + ", you have admin access!";
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        return adminService.getDashboard();
    }

    @GetMapping("/users")
    public PageResponse<AdminUserResponse> users(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return adminService.getUsers(keyword, page, size);
    }

    @PutMapping("/users/{id}/enable")
    public AdminUserResponse enableUser(@PathVariable("id") Long id,
                                        Authentication authentication) {
        return adminService.setUserEnabled(id, true, authentication.getName());
    }

    @PutMapping("/users/{id}/disable")
    public AdminUserResponse disableUser(@PathVariable("id") Long id,
                                         Authentication authentication) {
        return adminService.setUserEnabled(id, false, authentication.getName());
    }
}