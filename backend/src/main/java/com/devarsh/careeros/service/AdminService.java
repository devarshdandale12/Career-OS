package com.devarsh.careeros.service;

import com.devarsh.careeros.dto.AdminDashboardResponse;
import com.devarsh.careeros.dto.AdminUserResponse;
import com.devarsh.careeros.dto.PageResponse;

public interface AdminService {

    AdminDashboardResponse getDashboard();

    PageResponse<AdminUserResponse> getUsers(String keyword, int page, int size);

    AdminUserResponse setUserEnabled(Long userId, boolean enabled, String currentAdminEmail);
}