package com.realproj.tasklist.service;

import com.realproj.tasklist.web.dto.auth.JwtRequest;
import com.realproj.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
