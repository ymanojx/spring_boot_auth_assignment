package com.assignment.demo.service.impl;

import com.assignment.demo.entity.Role;
import com.assignment.demo.entity.User;
import com.assignment.demo.exception.AppException;
import com.assignment.demo.payload.LoginDto;
import com.assignment.demo.payload.RegisterDto;
import com.assignment.demo.repository.RoleRepository;
import com.assignment.demo.repository.UserRepository;
import com.assignment.demo.security.JwtTokenProvider;
import com.assignment.demo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        String usernameOrEmail = loginDto.getUsernameOrEmail();

        LOGGER.info(usernameOrEmail + " trying to login");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        loginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info(usernameOrEmail + " logged in successfully");

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        final String username = registerDto.getUsername();

        LOGGER.info(username + " trying to register");

        if(userRepository.existsByUsername(username)) {
            LOGGER.error("username: " + username + " already exists");
            throw new AppException(HttpStatus.BAD_REQUEST, "username already exists");
        }

        final String email = registerDto.getEmail();
        if(userRepository.existsByEmail(email)) {
            LOGGER.error("email: " + email + " already exists");
            throw new AppException(HttpStatus.BAD_REQUEST, "email already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        LOGGER.info(username + " registered successfully");

        return "user registered successfully";
    }
}
