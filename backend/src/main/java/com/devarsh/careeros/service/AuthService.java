package com.devarsh.careeros.service;

import com.devarsh.careeros.dto.AuthResponse;
import com.devarsh.careeros.dto.LoginRequest;
import com.devarsh.careeros.dto.RegisterRequest;
import com.devarsh.careeros.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}