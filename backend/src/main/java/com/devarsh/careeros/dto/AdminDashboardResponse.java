package com.devarsh.careeros.dto;

public record AdminDashboardResponse(
        long totalUsers,
        long admins,
        long regularUsers,
        long activeAccounts,
        long disabledAccounts,
        long newThisWeek
) {
}