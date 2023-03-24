package com.assignment.demo.service.impl;

import com.assignment.demo.payload.LoginDto;
import com.assignment.demo.payload.RegisterDto;
import com.assignment.demo.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String login(LoginDto loginDto) {
        return "manoj";
    }

    @Override
    public String register(RegisterDto registerDto) {
        return "user registered succesfully";
    }
}
