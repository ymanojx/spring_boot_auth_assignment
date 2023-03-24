package com.assignment.demo.service;

import com.assignment.demo.payload.LoginDto;
import com.assignment.demo.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
